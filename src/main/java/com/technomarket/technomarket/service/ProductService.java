package com.technomarket.technomarket.service;

import com.technomarket.technomarket.repository.product.ProductFilter;
import com.technomarket.technomarket.dto.products.ProductDto;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.entity.Product;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface ProductService {
    void createProduct(ProductDto productDto, Principal principal);

    void deleteProduct(Long productId, Principal principal);

    List<Product> getProducts(Integer pageNumber);

    Product getProductById(Long productId);

    void createReview(Long productId, ReviewDto reviewDto, Principal principal);

    Page<Product> getProductsByFilter(ProductFilter filter);


}
