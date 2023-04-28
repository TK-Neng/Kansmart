package com.int221.backend.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.int221.backend.entities.Category} entity
 */
@Data
public class CategoryDto implements Serializable {
    private final String categoryName;
}