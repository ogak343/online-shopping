package com.example.onlineshopping.mapper;

import com.example.onlineshopping.dto.request.CreateProductRequest;
import com.example.onlineshopping.dto.request.UpdateProductRequest;
import com.example.onlineshopping.dto.response.ProductResponse;
import com.example.onlineshopping.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setCategoryId(request.categoryId());
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setDescription(request.description());
        return product;
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategoryId());
    }

    public void update(Product product, UpdateProductRequest request) {
        if (request.name()!= null) {
            product.setName(request.name());
        }
        if (request.description()!= null) {
            product.setDescription(request.description());
        }
        if (request.price()!= null) {
            product.setPrice(request.price());
        }
        if (request.quantity()!= null) {
            product.setQuantity(request.quantity());
        }
    }
}
