package com.example.dims_project_2.repository;

import com.example.dims_project_2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
