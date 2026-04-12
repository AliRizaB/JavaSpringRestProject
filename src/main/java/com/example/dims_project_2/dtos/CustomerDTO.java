package com.example.dims_project_2.dtos;

import com.example.dims_project_2.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private long id;
    private String name;
    private String address;
    private String telephone;
}
