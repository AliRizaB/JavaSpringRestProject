package com.example.dims_project_2.dtos;

import com.example.dims_project_2.model.Product;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String supplier;
    private double price;
}
