package com.example.dims_project_2.model;

import com.example.dims_project_2.dtos.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
public class Customer {
    @Id
    // Auto Generating the id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 25)
    @NotBlank(message = "Please enter a name")
    private String name;

    @Column(length = 50)
    @NotBlank(message = "Please enter an address")
    private String address;

    @Column(length = 25)
    @NotBlank(message = "Please enter a telephone number")
    private String telephone;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Order> orders;

    public Customer(long id, String name, String address, String telephone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public CustomerDTO viewAsCustomerDTO(){
        return new CustomerDTO(id, name, address, telephone);
    }

}
