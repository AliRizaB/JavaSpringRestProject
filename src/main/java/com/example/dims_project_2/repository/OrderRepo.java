package com.example.dims_project_2.repository;

import com.example.dims_project_2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
