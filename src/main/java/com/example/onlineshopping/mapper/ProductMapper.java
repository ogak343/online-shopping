package com.example.onlineshopping.mapper;

import com.example.onlineshopping.dto.request.CreateProductRequest;
import com.example.onlineshopping.dto.request.UpdateProductRequest;
import com.example.onlineshopping.dto.response.ProductResponse;
import com.example.onlineshopping.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(CreateProductRequest request) {
        return null;
    }

    public ProductResponse toResponse(Product product) {
        return null;
    }

    public void update(Product product, UpdateProductRequest request) {

    }
}
