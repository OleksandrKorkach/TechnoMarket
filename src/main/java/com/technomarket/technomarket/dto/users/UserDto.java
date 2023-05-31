package com.technomarket.technomarket.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.dto.products.ProductSummaryDto;
import com.technomarket.technomarket.dto.reviews.ReviewContentDto;
import com.technomarket.technomarket.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime created;
    private List<ProductSummaryDto> products;
    private List<ReviewContentDto> reviews;

    public static UserDto fromUser(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreated(user.getCreated());

        dto.setProducts(user.getProducts().stream()
                .map(ProductSummaryDto::fromProduct)
                .collect(Collectors.toList()));

        dto.setReviews(user.getReviews().stream()
                .map(ReviewContentDto::fromReview)
                .collect(Collectors.toList()));
        return dto;
    }

}
