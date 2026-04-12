package com.example.dims_project_2.service;

import com.example.dims_project_2.dtos.CustomerDTO;
import com.example.dims_project_2.dtos.OrderDTO;
import com.example.dims_project_2.dtos.ProductDTO;
import com.example.dims_project_2.exception.AlreadyExistsError;
import com.example.dims_project_2.exception.ErrorMessages;
import com.example.dims_project_2.exception.NotFoundError;
import com.example.dims_project_2.model.Order;
import com.example.dims_project_2.repository.CustomerRepo;
import com.example.dims_project_2.repository.OrderRepo;
import com.example.dims_project_2.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService{
    private final OrderRepo repoOrder;
    private final CustomerRepo repoCustomer;
    private final ProductRepo repoProduct;

    OrderServiceImp(OrderRepo repoOrder, CustomerRepo repoCustomer, ProductRepo repoProduct){
        this.repoOrder = repoOrder;
        this.repoCustomer = repoCustomer;
        this.repoProduct = repoProduct;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return repoOrder.findAll().stream().map(Order::viewAsOrderDTO).toList();
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = repoOrder.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_ORDER_NOT_FOUND + " with id : " + id ));

        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getCity(), order.getDeliveryStatus(), order.getOrderDate());

        CustomerDTO customerDTO = new CustomerDTO(order.getCustomer().getId(), order.getCustomer().getName(), order.getCustomer().getAddress(), order.getCustomer().getTelephone());
        orderDTO.setCustomerDTO(customerDTO);

        ProductDTO productDTO = new ProductDTO(order.getProduct().getId(), order.getProduct().getName(), order.getProduct().getSupplier(), order.getProduct().getPrice());
        orderDTO.setProductDTO(productDTO);

        return  orderDTO;
    }

    @Override
    public OrderDTO createOrder(Order order) {
        if(repoOrder.findById(order.getId()).isPresent()){
            throw new AlreadyExistsError(ErrorMessages.ERROR_ORDER_ALREADY_EXIST + " with id : " + order.getId());
        }

        return repoOrder.save(order).viewAsOrderDTO();
    }

    @Override
    public OrderDTO updateOrder(Long id, Order order) {
        repoOrder.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_ORDER_NOT_FOUND + " with id : " + id ));
        order.setId(id);
        return repoOrder.save(order).viewAsOrderDTO();
    }

    @Override
    public void deleteOrder(Long id) {
        repoOrder.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_ORDER_NOT_FOUND + " with id : " + id ));
        repoOrder.deleteById(id);
    }
}
