package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.contants.OrderStatus;
import com.example.onlineshopping.dto.request.CreateOrderRequest;
import com.example.onlineshopping.dto.request.UpdateOrderRequest;
import com.example.onlineshopping.dto.response.OrderResponse;
import com.example.onlineshopping.entity.Order;
import com.example.onlineshopping.entity.OrderDetail;
import com.example.onlineshopping.entity.Product;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.OrderMapper;
import com.example.onlineshopping.repository.OrderRepository;
import com.example.onlineshopping.repository.ProductRepository;
import com.example.onlineshopping.utils.AppUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    public OrderService(OrderRepository repository,
                        ProductRepository productRepository,
                        OrderMapper mapper) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        Long userId = AppUtils.userId();
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        AtomicLong totalAmount = new AtomicLong(0);
        List<Product> products = new ArrayList<>();
        request.orderDetails().forEach(detail -> {
            Product product = productRepository.findById(detail.productId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
            if (detail.quantity() > product.getQuantity()) throw new CustomException(ErrorCode.INVALID_QUANTITY);
            totalAmount.set(totalAmount.get() + detail.quantity() + product.getPrice());
            orderDetails.add(new OrderDetail(product, detail.quantity()));
            product.setQuantity(product.getQuantity() - detail.quantity());
            products.add(product);
        });
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(0L);
        productRepository.saveAll(products);
        order = repository.save(order);
        order.setTotalPrice(totalAmount.get());
        Order finalOrder = order;
        orderDetails.forEach(orderDetail -> orderDetail.setOrder(finalOrder));
        order.setOrderDetails(orderDetails);
        return mapper.toResponse(repository.save(order));
    }

    @Transactional
    public OrderResponse cancel(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (OrderStatus.CREATED != order.getStatus()) throw new CustomException(ErrorCode.INVALID_ORDER_STATUS);
        List<Product> products = new ArrayList<>();
        order.getOrderDetails().forEach(detail -> {
            Product product = productRepository.findById(detail.getProduct().getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
            product.setQuantity(product.getQuantity() + detail.getQuantity());
            products.add(product);
        });
        productRepository.saveAll(products);
        order.setStatus(OrderStatus.CANCELLED);
        return mapper.toResponse(repository.save(order));
    }

    public List<OrderResponse> getAll() {
        return repository.findAllByUserId(AppUtils.userId())
                .stream().map(mapper::toResponse).toList();
    }

    public OrderResponse get(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND)));
    }

    public OrderResponse update(UpdateOrderRequest request) {
        return null;
    }
}
