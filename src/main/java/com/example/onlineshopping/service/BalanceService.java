package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.CreateBalanceRequest;
import com.example.onlineshopping.dto.request.FullFillBalanceRequest;
import com.example.onlineshopping.dto.response.BalanceResponse;
import com.example.onlineshopping.entity.Balance;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.BalanceMapper;
import com.example.onlineshopping.repository.BalanceRepository;
import com.example.onlineshopping.utils.AppUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BalanceService {

    private final BalanceRepository repository;
    private final BalanceMapper mapper;

    public BalanceService(BalanceRepository repository,
                          BalanceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public BalanceResponse create(CreateBalanceRequest request) {
        Balance balance = new Balance();
        balance.setUserId(request.userId());
        balance.setAmount(0L);
        balance.setCreatedAt(OffsetDateTime.now());
        return mapper.toResponse(repository.save(balance));
    }

    @Transactional
    public BalanceResponse fullFill(FullFillBalanceRequest request) {
        Balance balance = repository.findById(request.balanceId())
                .orElseThrow(() -> new CustomException(ErrorCode.BALANCE_NOT_FOUND));
        balance.setAmount(balance.getAmount() + request.amount());
        return mapper.toResponse(repository.save(balance));
    }

    public BalanceResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BALANCE_NOT_FOUND)));
    }

    public List<BalanceResponse> getAll() {
        return repository.findAllByUserId(AppUtils.userId()).stream().map(mapper::toResponse).toList();
    }

    public List<BalanceResponse> getByUserId(Long userId) {
        return repository.findAllByUserId(userId).stream().map(mapper::toResponse).toList();
    }
}
