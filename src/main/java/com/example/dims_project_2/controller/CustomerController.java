package com.example.dims_project_2.controller;

import com.example.dims_project_2.dtos.CustomerDTO;
import com.example.dims_project_2.model.Customer;
import com.example.dims_project_2.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String ShowCreateCustomerForm(Model model){
        // Need to send an empty object for it to work.
        model.addAttribute("customer", new CustomerDTO());
        return "Customer/customer_create";
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String SubmitCreateCustomerForm(@Valid @ModelAttribute("customer") Customer customer, BindingResult result){
        if(result.hasErrors()){
            return "Customer/customer_create";
        }
        customerService.createCustomer(customer);
        return "redirect:/customer/read";
    }

    @GetMapping("/read")
    public String ShowReadCustomer(Model model){
        List<CustomerDTO> customers = customerService.getAllCustomers();
        model.addAttribute("Customers", customers);
        return "Customer/customer_read";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String ShowDeleteCustomer(Model model, @PathVariable Long id){
        try {
            CustomerDTO customer = customerService.getCustomerById(id);
            model.addAttribute("customer", customer);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Customer/customer_delete";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String DeleteCustomer(Model model, @PathVariable Long id){
        customerService.deleteCustomer(id);
        return "redirect:/customer/read";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String ShowUpdateCustomer(Model model, @PathVariable Long id){
        try {
            CustomerDTO customer = customerService.getCustomerById(id);
            model.addAttribute("customer", customer);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Customer/customer_update";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String UpdateCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return "Customer/customer_update";
        }
        customerService.updateCustomer(id, customer);
        return "redirect:/customer/read";
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String GetCustomerInfo(Model model ,@PathVariable Long id){
        try{
            CustomerDTO customer = customerService.getCustomerById(id);
            model.addAttribute("customer", customer);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Customer/customer_info";
    }
}
