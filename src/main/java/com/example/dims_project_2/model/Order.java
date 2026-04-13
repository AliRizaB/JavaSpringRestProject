package com.example.dims_project_2.model;

import com.example.dims_project_2.dtos.OrderDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 25)
    @NotBlank(message = "Please enter a city")
    private String city;

    @Column(length = 20)
    private String deliveryStatus;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please select a valid date")
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Product product;

    public OrderDTO viewAsOrderDTO(){
        return new OrderDTO(id, city, deliveryStatus, orderDate, customer.viewAsCustomerDTO(), product.viewAsProductDTO());
    }

}
