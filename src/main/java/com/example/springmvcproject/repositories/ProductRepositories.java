package com.example.springmvcproject.repositories;

import com.example.springmvcproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositories extends JpaRepository<Product, Long> {
    List<Product> findByCategories(String categories);
}
