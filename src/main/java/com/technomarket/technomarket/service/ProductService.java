package com.technomarket.technomarket.service;

import com.technomarket.technomarket.entity.Feedback;
import com.technomarket.technomarket.entity.Product;

import java.security.Principal;
import java.util.List;

public interface ProductService {
    void createProduct(Product product, Principal principal);

    void deleteProduct(Long id);

    List<Product> getProducts();

    Product getProductById(Long id);

    void createFeedback(Product product, Feedback feedback, Principal principal);


}
