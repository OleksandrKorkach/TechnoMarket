package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.entity.Feedback;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.entity.enums.Status;
import com.technomarket.technomarket.repository.FeedbackRepository;
import com.technomarket.technomarket.repository.ProductRepository;
import com.technomarket.technomarket.repository.UserRepository;
import com.technomarket.technomarket.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void createProduct(Product product, Principal principal) {
        product.setCategory(null);
        product.setUser(getUserByPrincipal(principal));
        log.info("Saving new Product. Title: {}; Owner username: {}", product.getTitle(), product.getUser().getUsername());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByUsername(principal.getName());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void createFeedback(Product product, Feedback feedback, Principal principal) {
        feedback.setProduct(product);
        feedback.setUser(getUserByPrincipal(principal));
        log.info("Saving new feedback. Text: {}; Author username: {}", feedback.getText(), feedback.getUser().getUsername());
        feedbackRepository.save(feedback);
    }


}
