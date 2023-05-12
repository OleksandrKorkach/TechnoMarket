package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.FeedbackDto;
import com.technomarket.technomarket.dto.ProductDto;
import com.technomarket.technomarket.entity.Feedback;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/create/product")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, Principal principal){
        Product product = productDto.toProduct();
        productService.createProduct(product, principal);
        return ResponseEntity.ok("successfully created");
    }

    @PostMapping("/delete/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @PostMapping("/comment/product/{id}")
    public ResponseEntity<?> giveFeedback(@PathVariable Long id, @RequestBody FeedbackDto feedbackDto, Principal principal){
        Feedback feedback = feedbackDto.toFeedback();
        Product product = productService.getProductById(id);
        productService.createFeedback(product, feedback, principal);
        return ResponseEntity.ok("Feedback successfully created!");
    }
}
