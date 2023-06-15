package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.dto.products.ProductSummaryDto;
import com.technomarket.technomarket.entity.product.ProductFilter;
import com.technomarket.technomarket.dto.products.ProductDto;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.entity.Review;
import com.technomarket.technomarket.entity.product.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.repository.ReviewRepository;
import com.technomarket.technomarket.repository.ProductRepository;
import com.technomarket.technomarket.service.ProductService;
import com.technomarket.technomarket.service.UserService;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ReviewRepository reviewRepository;
    private final Integer pageSize = 4;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void createProduct(ProductDto productDto, Principal principal) {
        Product product = productDto.toProduct();
        product.setUser(userService.findUserByPrincipal(principal));
        log.info("Saving new Product. Title: {}; Owner username: {}", product.getTitle(), product.getUser().getUsername());
        productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id, Principal principal) {
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
    public List<Product> findProducts(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public List<ProductSummaryDto> getProductsSummaryDtoByFilter(ProductFilter filter){
        Page<Product> productPage = getProductsPageByFilter(filter);
        return productPage.stream()
                .map(ProductSummaryDto::fromProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Product> getProductsPageByFilter(ProductFilter filter) {
        Sort sort;
        int pageNumber = filter.getPageNumber() != null ? filter.getPageNumber() : 0;
        if (filter.getAscending() == null || !filter.getAscending()){
            sort = Sort.by(Sort.Direction.DESC, "price");
        } else {
            sort = Sort.by(Sort.Direction.ASC,"price");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return productRepository.findAllByFilter(filter, pageable);
    }

    @Override
    public ProductDto getProductDtoById(Long id){
        Product product = findProductById(id);
        return ProductDto.fromProduct(product);
    }

    @Override
    public Product findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null){
            throw new ResourceNotFoundException("Product not found with id: " + id);
        } else {
            return product;
        }
    }

    @Override
    public void createReviewForProduct(ReviewDto reviewDto, Long productId) {
        Review review = reviewDto.toReview();
        review.setProduct(findProductById(productId));
        review.setUser(userService.findUserByPrincipal(userService.getPrincipal()));
        log.info("Saving new feedback. Text: {}; Author username: {}", review.getText(), review.getUser().getUsername());
        reviewRepository.save(review);
    }


}
