package com.example.dims_project_2.service;

import com.example.dims_project_2.dtos.CustomerDTO;
import com.example.dims_project_2.model.Customer;

import java.util.List;

public interface CustomerService {
    public List<CustomerDTO> getAllCustomers();
    public CustomerDTO getCustomerById(Long id);
    public CustomerDTO createCustomer(Customer customer);
    public CustomerDTO updateCustomer(Long id, Customer customer);
    public void deleteCustomer(Long id);
}
