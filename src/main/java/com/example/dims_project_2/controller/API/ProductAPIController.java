package com.example.dims_project_2.controller.API;

import com.example.dims_project_2.dtos.ProductDTO;
import com.example.dims_project_2.model.Product;
import com.example.dims_project_2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductAPIController {
    private final ProductService productService;

    public ProductAPIController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (id == null || id == 0 || product == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
