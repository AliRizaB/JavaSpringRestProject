package com.example.dims_project_2.controller.API;

import com.example.dims_project_2.dtos.OrderDTO;
import com.example.dims_project_2.model.Order;
import com.example.dims_project_2.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderAPIController {
    private final OrderService orderService;

    public OrderAPIController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderDTO> addOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        if (id == null || id == 0 || order == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(orderService.updateOrder(id, order), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
