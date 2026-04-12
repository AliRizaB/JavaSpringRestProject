package com.example.dims_project_2.model;

import com.example.dims_project_2.dtos.ProductDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 25)
    @NotBlank(message = "Please enter a name")
    private String name;

    @Column(length = 25)
    @NotBlank(message = "Please enter a supplier")
    private String supplier;

    @Column
    @NotNull(message = "Please enter a price value")
    @DecimalMin(value = "0.0" , message = "Price must be larger than zero")
    private double price;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

    public Product(long id, String name, String supplier, double price) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
        this.price = price;
    }

    public ProductDTO viewAsProductDTO(){
        return new ProductDTO(id, name, supplier, price);
    }
}
