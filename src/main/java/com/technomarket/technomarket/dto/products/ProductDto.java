package com.technomarket.technomarket.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.dto.reviews.ReviewDto;
import com.technomarket.technomarket.dto.users.UserProductOwnerDto;
import com.technomarket.technomarket.entity.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean in_stock;
    private UserProductOwnerDto user;
    private List<ReviewDto> reviews;

    public Product toProduct(){
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setInStock(in_stock);
        return product;
    }

    public static ProductDto fromProduct(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setIn_stock(product.getInStock());
        dto.setUser(UserProductOwnerDto.fromUser(product.getUser()));
        dto.setReviews(product.getReviews().stream()
                .map(ReviewDto::fromReview)
                .collect(Collectors.toList()));
        return dto;
    }
}
