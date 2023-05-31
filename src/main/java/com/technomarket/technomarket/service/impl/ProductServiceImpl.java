package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.entity.Review;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.repository.ReviewRepository;
import com.technomarket.technomarket.repository.ProductRepository;
import com.technomarket.technomarket.repository.UserRepository;
import com.technomarket.technomarket.service.ProductService;
import com.technomarket.technomarket.service.impl.exceptions.ResourceNotFoundException;
import com.technomarket.technomarket.service.impl.exceptions.UnauthorizedProductAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final Integer pageSize = 20;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void createProduct(Product product, Principal principal) {
        product.setUser(getUserByPrincipal(principal));
        log.info("Saving new Product. Title: {}; Owner username: {}", product.getTitle(), product.getUser().getUsername());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedProductAccessException("Unauthorized request, please login first");
        }
        return userRepository.findByUsername(principal.getName());
    }

    @Override
    public void deleteProduct(Long id, Principal principal) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null){
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        User owner  = product.getUser();
        if (owner.getUsername().equals(principal.getName())){
            productRepository.deleteById(id);
            log.info("Product with id {} was deleted by {}", id, owner);
        } else {
            throw new UnauthorizedProductAccessException("You don't have enough authority to delete this product");
        }
    }

    @Override
    public List<Product> getProducts(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null){
            throw new ResourceNotFoundException("Product not found with id: " + id);
        } else {
            return product;
        }

    }

    @Override
    public void createReview(Product product, Review review, Principal principal) {
        review.setProduct(product);
        review.setUser(getUserByPrincipal(principal));
        log.info("Saving new feedback. Text: {}; Author username: {}", review.getText(), review.getUser().getUsername());
        reviewRepository.save(review);
    }


}
