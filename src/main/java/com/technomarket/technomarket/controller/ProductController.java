package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.products.ProductDto;
import com.technomarket.technomarket.dto.products.ProductSummaryDto;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.entity.Review;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping("/product{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        ProductDto productDto = ProductDto.fromProduct(product);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductSummaryDto>> getAllProducts(){
        List<Product> productsFromDb = productService.getProducts();
        List<ProductSummaryDto> products = productsFromDb.stream()
                .map(ProductSummaryDto::fromProduct)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, Principal principal){
        Product product = productDto.toProduct();
        productService.createProduct(product, principal);
        return ResponseEntity.ok("successfully created");
    }

    @PostMapping("/product{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Principal principal){
        productService.deleteProduct(id, principal);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @PostMapping("/product{id}/review")
    public ResponseEntity<?> giveReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto, Principal principal){
        Review review = reviewDto.toReview();
        Product product = productService.getProductById(id);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        productService.createReview(product, review, principal);
        return ResponseEntity.ok("Review successfully created!");
    }

}
