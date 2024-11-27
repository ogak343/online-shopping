package com.example.onlineshopping.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "product")
@Setter
@Getter
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Integer quantity;
    private Long categoryId;
}
