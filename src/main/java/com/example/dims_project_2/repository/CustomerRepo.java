package com.example.dims_project_2.repository;

import com.example.dims_project_2.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
