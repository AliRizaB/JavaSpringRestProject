package com.example.dims_project_2.controller;

import com.example.dims_project_2.dtos.ProductDTO;
import com.example.dims_project_2.model.Product;
import com.example.dims_project_2.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String ShowCreateProductForm(Model model){
        // Need to send an empty object for it to work.
        model.addAttribute("product", new ProductDTO());
        return "Product/product_create";
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String SubmitCreateProductForm(@Valid @ModelAttribute("product") Product product, BindingResult result){
        if(result.hasErrors()){
            return "Product/product_create";
        }
        productService.createProduct(product);
        return "redirect:/product/read";
    }

    @GetMapping("/read")
    public String ShowReadProduct(Model model){
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("Products", products);
        return "Product/product_read";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String ShowDeleteProduct(Model model, @PathVariable Long id){
        try {
            ProductDTO product = productService.getProductById(id);
            model.addAttribute("product", product);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Product/product_delete";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String DeleteProduct(Model model, @PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/product/read";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String ShowUpdateProduct(Model model, @PathVariable Long id){
        try {
            ProductDTO product = productService.getProductById(id);
            model.addAttribute("product", product);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Product/product_update";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String UpdateProduct(@Valid @ModelAttribute("product") Product product, BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return "Product/product_update";
        }
        productService.updateProduct(id, product);
        return "redirect:/product/read";
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String GetProductInfo(Model model ,@PathVariable Long id){
        try{
            ProductDTO product = productService.getProductById(id);
            model.addAttribute("product", product);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "Product/product_info";
    }
}
