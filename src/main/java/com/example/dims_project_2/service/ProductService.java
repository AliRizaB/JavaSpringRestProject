package com.example.dims_project_2.service;


import com.example.dims_project_2.dtos.ProductDTO;
import com.example.dims_project_2.model.Product;

import java.util.List;

public interface ProductService {
    public List<ProductDTO> getAllProducts();
    public ProductDTO getProductById(Long id);
    public ProductDTO createProduct(Product customer);
    public ProductDTO updateProduct(Long id, Product customer);
    public void deleteProduct(Long id);
}
