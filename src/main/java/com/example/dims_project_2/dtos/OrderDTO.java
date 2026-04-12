package com.example.dims_project_2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long id;
    private String city;
    private String deliveryStatus;
    private LocalDate orderDate;
    public CustomerDTO customerDTO;
    public ProductDTO productDTO;

    public OrderDTO(long id, String city, String deliveryStatus, LocalDate orderDate ) {
        this.id = id;
        this.city = city;
        this.deliveryStatus = deliveryStatus;
        this.orderDate = orderDate;
    }
}
