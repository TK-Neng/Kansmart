package com.example.back.Repositories;

import com.example.back.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategorieRepositories extends JpaRepository<Categories, Integer> {
//    @Query(value = "select * from categories c join announces a on c.categoryId=a.categories_categoryId", nativeQuery = true)
}
