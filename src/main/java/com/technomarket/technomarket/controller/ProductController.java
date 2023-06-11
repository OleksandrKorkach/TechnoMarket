package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.repository.product.ProductFilter;
import com.technomarket.technomarket.dto.products.ProductDto;
import com.technomarket.technomarket.dto.products.ProductSummaryDto;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product-{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductDto productDto = ProductDto.fromProduct(product);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@ModelAttribute("filter") ProductFilter filter){
        Page<Product> products = productService.getProductsByFilter(filter);
        List<ProductDto> response = products.stream()
                .map(ProductDto::fromProduct)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, Principal principal) {
        productService.createProduct(productDto, principal);
        return ResponseEntity.ok("successfully created");
    }

    @PostMapping("/product{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(id, principal);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @PostMapping("/product{productId}/review")
    public ResponseEntity<?> giveReview(@PathVariable Long productId, @RequestBody ReviewDto reviewDto, Principal principal) {
        productService.createReview(productId, reviewDto, principal);
        return ResponseEntity.ok("Review successfully created!");
    }

}
