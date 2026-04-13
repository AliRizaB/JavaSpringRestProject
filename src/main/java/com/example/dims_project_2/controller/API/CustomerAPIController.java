package com.example.dims_project_2.controller.API;

import com.example.dims_project_2.dtos.CustomerDTO;
import com.example.dims_project_2.model.Customer;
import com.example.dims_project_2.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerAPIController {
    private final CustomerService customerService;

    public CustomerAPIController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        if (id == null || id == 0 || customer == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
