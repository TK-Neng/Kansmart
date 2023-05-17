package com.int221.backend.repositories;

import com.int221.backend.dto.CategoryDto;
import com.int221.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}