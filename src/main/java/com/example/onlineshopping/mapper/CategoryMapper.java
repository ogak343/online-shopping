package com.example.onlineshopping.mapper;

import com.example.onlineshopping.dto.request.CreateCategoryRequest;
import com.example.onlineshopping.dto.request.UpdateCategoryRequest;
import com.example.onlineshopping.dto.response.CategoryResponse;
import com.example.onlineshopping.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CreateCategoryRequest request) {
        Category entity = new Category();
        entity.setName(request.name());
        entity.setDescription(request.description());
        return entity;
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    public void update(Category entity, UpdateCategoryRequest request) {
        if (request.name() != null) entity.setName(request.name());
        if (request.description() != null) entity.setDescription(request.description());
    }
}
