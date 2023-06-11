package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.repository.product.ProductFilter;
import com.technomarket.technomarket.dto.products.ProductDto;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.entity.Review;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.repository.ReviewRepository;
import com.technomarket.technomarket.repository.product.ProductRepository;
import com.technomarket.technomarket.repository.UserRepository;
import com.technomarket.technomarket.service.ProductService;
import com.technomarket.technomarket.service.impl.exceptions.ResourceNotFoundException;
import com.technomarket.technomarket.service.impl.exceptions.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final Integer pageSize = 4;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void createProduct(ProductDto productDto, Principal principal) {
        Product product = productDto.toProduct();
        product.setUser(getUserByPrincipal(principal));
        log.info("Saving new Product. Title: {}; Owner username: {}", product.getTitle(), product.getUser().getUsername());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedAccessException("Unauthorized request, please login first");
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
            throw new UnauthorizedAccessException("You don't have enough authority to delete this product");
        }
    }

    @Override
    public List<Product> getProducts(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public Page<Product> getProductsByFilter(ProductFilter filter) {
        Sort sort;
        Integer pageNumber = filter.getPageNumber();
        if (pageNumber == null){
            pageNumber = 0;
        }

        if (filter.getAscending() == null || !filter.getAscending()){
            sort = Sort.by(Sort.Direction.DESC, "price");
        } else {
            sort = Sort.by(Sort.Direction.ASC,"price");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return productRepository.findAllByFilter(filter, pageable);
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
    public void createReview(Long productId, ReviewDto reviewDto, Principal principal) {
        Product product = getProductById(productId);
        Review review = reviewDto.toReview();
        review.setProduct(product);
        review.setUser(getUserByPrincipal(principal));
        log.info("Saving new feedback. Text: {}; Author username: {}", review.getText(), review.getUser().getUsername());
        reviewRepository.save(review);
    }


}
