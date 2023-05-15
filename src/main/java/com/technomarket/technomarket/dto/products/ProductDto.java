package com.technomarket.technomarket.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.dto.users.UserDto;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.Review;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.entity.enums.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private String title;
    private String description;
    private Double price;
    private Boolean in_stock;
    private Integer quantity;
    private UserDto user;
    private List<ReviewDto> reviews;


    public Product toProduct(){
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setInStock(in_stock);
        product.setQuantity(quantity);

        return product;
    }

    public static ProductDto fromProduct(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setIn_stock(product.getInStock());
        productDto.setQuantity(product.getQuantity());
        productDto.setUser(UserDto.fromUser(product.getUser()));
        List<ReviewDto> reviewDtoList = product.getReviews().stream()
                .map(ReviewDto::fromReview)
                .collect(Collectors.toList());
        productDto.setReviews(reviewDtoList);
        return productDto;
    }
}
