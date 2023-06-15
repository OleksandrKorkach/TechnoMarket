package com.technomarket.technomarket.service;

import com.technomarket.technomarket.dto.products.ProductSummaryDto;
import com.technomarket.technomarket.entity.product.ProductFilter;
import com.technomarket.technomarket.dto.products.ProductDto;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.entity.product.Product;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface ProductService {
    void createProduct(ProductDto productDto, Principal principal);

    void deleteProductById(Long productId, Principal principal);

    List<Product> findProducts(Integer pageNumber);

    ProductDto getProductDtoById(Long productId);

    Product findProductById(Long id);

    void createReviewForProduct(ReviewDto reviewDto, Long productId);

    List<ProductSummaryDto> getProductsSummaryDtoByFilter(ProductFilter filter);

    Page<Product> getProductsPageByFilter(ProductFilter filter);


}
