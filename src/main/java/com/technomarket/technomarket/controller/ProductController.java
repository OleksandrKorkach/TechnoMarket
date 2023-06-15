package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.products.ProductSummaryDto;
import com.technomarket.technomarket.entity.product.ProductFilter;
import com.technomarket.technomarket.dto.products.ProductDto;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product-{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductDtoById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductSummaryDto>> getFilteredProducts(@ModelAttribute("filter") ProductFilter filter){
        List<ProductSummaryDto> products = productService.getProductsSummaryDtoByFilter(filter);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, Principal principal) {
        productService.createProduct(productDto, principal);
        return ResponseEntity.ok("successfully created");
    }

    @PostMapping("/product{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProductById(id, principal);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @PostMapping("/product{productId}/review")
    public ResponseEntity<?> giveReview(@PathVariable Long productId, @RequestBody ReviewDto reviewDto) {
        productService.createReviewForProduct(reviewDto, productId);
        return ResponseEntity.ok("Review successfully created!");
    }

}
