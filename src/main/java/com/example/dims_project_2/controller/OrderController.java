package com.example.dims_project_2.controller;

import com.example.dims_project_2.dtos.CustomerDTO;
import com.example.dims_project_2.dtos.OrderDTO;
import com.example.dims_project_2.dtos.ProductDTO;
import com.example.dims_project_2.model.Customer;
import com.example.dims_project_2.model.Order;
import com.example.dims_project_2.model.Product;
import com.example.dims_project_2.service.CustomerService;
import com.example.dims_project_2.service.OrderService;
import com.example.dims_project_2.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderController(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @GetMapping("/create")
    public String ShowCreateOrderForm(Model model) {
        Map<String, ?> lists = getLists();
        model.addAllAttributes(lists);

        // Need to send an empty object for it to work.
        model.addAttribute("order", new Order());

        return "Order/order_create";
    }

    @PostMapping("/create")
    public String SubmitCreateOrderForm(@Valid @ModelAttribute("order") Order order, BindingResult result) {
        System.out.println("MADE TO THE POST CREATE");

        if (result.hasErrors()) {
            return "Order/order_create";
        }

        try {
            orderService.createOrder(findConnections(order));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PAST THE CREATION TO THE POST CREATE");

        return "redirect:/order/read";
    }

    @GetMapping("/read")
    public String ShowReadOrder(Model model) {
        List<OrderDTO> orders = orderService.getAllOrders();
        model.addAttribute("Orders", orders);
        return "Order/order_read";
    }

    @GetMapping("/delete/{id}")
    public String ShowDeleteOrder(Model model, @PathVariable Long id) {
        try {
            OrderDTO order = orderService.getOrderById(id);
            model.addAttribute("order", order);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Order/order_delete";
    }

    @PostMapping("/delete/{id}")
    public String DeleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order/read";
    }

    @GetMapping("/update/{id}")
    public String ShowUpdateOrder(Model model, @PathVariable Long id) {
        try {
            Map<String, ?> lists = getLists();
            model.addAllAttributes(lists);

            OrderDTO order = orderService.getOrderById(id);
            model.addAttribute("order", order);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Order/order_update";
    }

    @PostMapping("/update/{id}")
    public String UpdateOrder(Model model, @Valid @ModelAttribute("order") Order order, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            Map<String, ?> lists = getLists();
            model.addAllAttributes(lists);
            return "Order/order_update";
        }
        orderService.updateOrder(id, findConnections(order));
        return "redirect:/order/read";
    }

    @GetMapping("/info/{id}")
    public String GetOrderInfo(Model model, @PathVariable Long id) {
        try {
            OrderDTO order = orderService.getOrderById(id);
            model.addAttribute("order", order);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Order/order_info";
    }

    private Order findConnections(Order order) {

        CustomerDTO customerDTO = customerService.getCustomerById(order.getCustomer().getId());
        Customer customer = new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getTelephone()
        );
        order.setCustomer(customer);

        ProductDTO productDTO = productService.getProductById(order.getProduct().getId());

        Product product = new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getSupplier(),
                productDTO.getPrice()
        );
        order.setProduct(product);

        return order;
    }

    private Map<String, ?> getLists() {
        return Map.of(
                "customers", customerService.getAllCustomers(),
                "products", productService.getAllProducts(),
                "allStats", List.of("PENDING", "SHIPPED", "DELIVERED", "CANCELLED")
        );
    }
}
