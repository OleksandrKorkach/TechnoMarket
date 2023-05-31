package com.technomarket.technomarket.service;

import com.technomarket.technomarket.entity.Review;
import com.technomarket.technomarket.entity.Product;

import java.security.Principal;
import java.util.List;

public interface ProductService {
    void createProduct(Product product, Principal principal);

    void deleteProduct(Long id, Principal principal);

    List<Product> getProducts(Integer pageNumber);

    Product getProductById(Long id);

    void createReview(Product product, Review review, Principal principal);


}
