package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.CreateCategoryRequest;
import com.example.onlineshopping.dto.request.UpdateCategoryRequest;
import com.example.onlineshopping.dto.response.CategoryResponse;
import com.example.onlineshopping.entity.Category;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.CategoryMapper;
import com.example.onlineshopping.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository,
                           CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CategoryResponse create(CreateCategoryRequest request) {
        boolean exists = repository.existsByName(request.name());
        if (exists) throw new CustomException(ErrorCode.CATEGORY_EXISTS);
        Category category = mapper.toEntity(request);
        return mapper.toResponse(repository.save(category));
    }

    public CategoryResponse update(UpdateCategoryRequest request, Long id) {
        Category entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        mapper.update(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    public CategoryResponse get(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)));
    }

    public List<CategoryResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
