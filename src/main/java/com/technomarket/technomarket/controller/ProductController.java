package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok().body("Deleted successfully!");
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveProduct(@RequestBody Product product){
        productService.saveProduct(product);
        return ResponseEntity.ok(product);
    }


}
