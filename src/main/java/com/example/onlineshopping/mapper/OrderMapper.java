package com.example.onlineshopping.mapper;

import com.example.onlineshopping.dto.response.OrderResponse;
import com.example.onlineshopping.entity.Order;
import com.example.onlineshopping.entity.OrderDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(), order.getUserId(), mapDetails(order.getOrderDetails()), order.getTotalPrice(), order.getStatus());
    }

    private List<OrderResponse.OrderDetailResponse> mapDetails(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(detail ->
                new OrderResponse.OrderDetailResponse(detail.getId(), detail.getOrder().getId(), detail.getProduct().getId(), detail.getQuantity())).toList();
    }
}
