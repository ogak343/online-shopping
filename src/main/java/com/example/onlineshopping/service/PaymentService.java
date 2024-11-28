package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.contants.OrderStatus;
import com.example.onlineshopping.contants.PaymentStatus;
import com.example.onlineshopping.dto.request.CreatePaymentRequest;
import com.example.onlineshopping.dto.request.PerformPaymentRequest;
import com.example.onlineshopping.dto.response.PaymentResponse;
import com.example.onlineshopping.dto.response.UserResponse;
import com.example.onlineshopping.entity.Balance;
import com.example.onlineshopping.entity.Order;
import com.example.onlineshopping.entity.Payment;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.PaymentMapper;
import com.example.onlineshopping.repository.BalanceRepository;
import com.example.onlineshopping.repository.OrderRepository;
import com.example.onlineshopping.repository.PaymentRepository;
import com.example.onlineshopping.utils.AppUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentMapper mapper;
    private final NotificationService notificationService;
    private final PaymentRepository repository;
    private final BalanceRepository balanceRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final SecureRandom secureRandom;

    public PaymentService(PaymentMapper mapper,
                          NotificationService notificationService,
                          PaymentRepository repository,
                          BalanceRepository balanceRepository,
                          OrderRepository orderRepository,
                          UserService userService, SecureRandom secureRandom
    ) {
        this.mapper = mapper;
        this.notificationService = notificationService;
        this.repository = repository;
        this.balanceRepository = balanceRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.secureRandom = secureRandom;
    }

    @Transactional
    public PaymentResponse perform(PerformPaymentRequest request) {
        Payment payment = repository.findById(request.paymentId())
                .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));
        Balance balance = balanceRepository.findById(payment.getBalanceToken())
                .orElseThrow(() -> new CustomException(ErrorCode.BALANCE_NOT_FOUND));
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        UserResponse profile = userService.profile();
        if (OrderStatus.HOLD != order.getStatus()) throw new CustomException(ErrorCode.INVALID_ORDER_STATUS);
        if (balance.getAmount() < payment.getAmount()) throw new CustomException(ErrorCode.NOT_ENOUGH_MONEY);
        if (!payment.getConfirmId().equals(request.confirmCode())) throw new CustomException(ErrorCode.INVALID_OTP_CODE);
        order.setStatus(OrderStatus.PAID);
        balance.setAmount(balance.getAmount() - payment.getAmount());
        payment.setStatus(PaymentStatus.PERFORMED);
        payment.setPaidAt(OffsetDateTime.now());
        payment = repository.save(payment);
        balanceRepository.save(balance);
        orderRepository.save(order);
        notificationService.sendPaymentPerformInfo(payment, profile.email());
        return mapper.toResponse(payment);
    }

    @Transactional
    public PaymentResponse init(CreatePaymentRequest request) {
        Balance balance = balanceRepository.findById(request.balanceId())
                .orElseThrow(() -> new CustomException(ErrorCode.BALANCE_NOT_FOUND));
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        UserResponse profile = userService.profile();
        if (OrderStatus.CREATED != order.getStatus()) throw new CustomException(ErrorCode.INVALID_ORDER_STATUS);
        if (!profile.id().equals(order.getUserId())) throw new CustomException(ErrorCode.ACCESS_DENIED);
        if (balance.getAmount() < order.getTotalPrice()) throw new CustomException(ErrorCode.NOT_ENOUGH_MONEY);
        Payment payment = new Payment();
        payment.setAmount(order.getTotalPrice());
        payment.setStatus(PaymentStatus.CREATED);
        payment.setBalanceToken(balance.getToken());
        payment.setOrderId(order.getId());
        payment.setUserId(profile.id());
        payment.setConfirmId(secureRandom.nextLong(100000, 999999));
        payment = repository.save(payment);
        order.setStatus(OrderStatus.HOLD);
        orderRepository.save(order);
        notificationService.sendPaymentInitInfo(payment, profile.email());
        return mapper.toResponse(payment);
    }

    public PaymentResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND)));
    }

    public List<PaymentResponse> getAll() {
        return repository.findByUserId(AppUtils.userId())
                .stream().map(mapper::toResponse).toList();
    }
}
