package com.example.dims_project_2.service;

import com.example.dims_project_2.dtos.ProductDTO;
import com.example.dims_project_2.exception.AlreadyExistsError;
import com.example.dims_project_2.exception.ErrorMessages;
import com.example.dims_project_2.exception.NotFoundError;
import com.example.dims_project_2.model.Product;
import com.example.dims_project_2.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImp implements ProductService{
    private final ProductRepo repo;

    public ProductsServiceImp(ProductRepo repo){
        this.repo = repo;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return repo.findAll().stream().map(Product::viewAsProductDTO).toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_PRODUCT_NOT_FOUND + "with id : " + id)).viewAsProductDTO();
    }

    @Override
    public ProductDTO createProduct(Product product) {
        if(repo.findById(product.getId()).isPresent()) {
            throw new AlreadyExistsError(ErrorMessages.ERROR_PRODUCT_ALREADY_EXIST + "with id : " + product.getId());
        }
        return repo.save(product).viewAsProductDTO();
    }

    @Override
    public ProductDTO updateProduct(Long id, Product product) {
        repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_PRODUCT_NOT_FOUND + "with id : " + id));
        product.setId(id);
        return repo.save(product).viewAsProductDTO();
    }

    @Override
    public void deleteProduct(Long id) {
        repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_PRODUCT_NOT_FOUND + "with id : " + id));
        repo.deleteById(id);
    }
}
