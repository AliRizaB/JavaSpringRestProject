package com.example.dims_project_2.service;

import com.example.dims_project_2.dtos.CustomerDTO;
import com.example.dims_project_2.exception.AlreadyExistsError;
import com.example.dims_project_2.exception.ErrorMessages;
import com.example.dims_project_2.exception.NotFoundError;
import com.example.dims_project_2.model.Customer;
import com.example.dims_project_2.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService{
    private final CustomerRepo repo;

    public CustomerServiceImp(CustomerRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return repo.findAll().stream().map(Customer::viewAsCustomerDTO).toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_CUSTOMER_NOT_FOUND + " with id : " + id)).viewAsCustomerDTO();
    }

    @Override
    public CustomerDTO createCustomer(Customer customer) {
        if(repo.findById(customer.getId()).isPresent()) {
            throw new AlreadyExistsError(ErrorMessages.ERROR_CUSTOMER_ALREADY_EXIST + " with id : " + customer.getId());
        }
        return repo.save(customer).viewAsCustomerDTO();
    }

    @Override
    public CustomerDTO updateCustomer(Long id, Customer customer) {
        repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_CUSTOMER_NOT_FOUND + " with id : " + id));
        customer.setId(id);
        return repo.save(customer).viewAsCustomerDTO();
    }

    @Override
    public void deleteCustomer(Long id) {
        repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_CUSTOMER_NOT_FOUND + " with id : " + id));
        repo.deleteById(id);
    }
}
