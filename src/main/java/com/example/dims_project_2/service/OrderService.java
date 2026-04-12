package com.example.dims_project_2.service;

import com.example.dims_project_2.dtos.OrderDTO;
import com.example.dims_project_2.model.Order;

import java.util.List;

public interface OrderService {
    public List<OrderDTO> getAllOrders();
    public OrderDTO getOrderById(Long id);
    public OrderDTO createOrder(Order order);
    public OrderDTO updateOrder(Long id, Order order);
    public void deleteOrder(Long id);
}
