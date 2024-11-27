package com.example.onlineshopping.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "category")
@Setter
@Getter
public class Category {
    @Id
    private Long id;
    private String name;
    private String description;
}
