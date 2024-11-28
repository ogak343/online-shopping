package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.CreateProductRequest;
import com.example.onlineshopping.dto.request.UpdateProductRequest;
import com.example.onlineshopping.dto.response.ProductResponse;
import com.example.onlineshopping.entity.Product;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.ProductMapper;
import com.example.onlineshopping.repository.CategoryRepository;
import com.example.onlineshopping.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository,
                          ProductMapper mapper,
                          CategoryRepository categoryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse create(CreateProductRequest request) {
        boolean exists = categoryRepository.existsById(request.categoryId());
        if (!exists) throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    public ProductResponse update(UpdateProductRequest request, Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        mapper.update(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    public ProductResponse get(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND)));
    }

    public List<ProductResponse> getCategoryProducts(Long categoryId) {
        return repository.findAllByCategoryId(categoryId)
                .stream().map(mapper::toResponse).toList();
    }
}
