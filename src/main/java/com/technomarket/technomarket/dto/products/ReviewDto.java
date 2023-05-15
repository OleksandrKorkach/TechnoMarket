package com.technomarket.technomarket.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.dto.users.UserDto;
import com.technomarket.technomarket.entity.Review;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDto {
    private String text;
    private Integer rate;
    private UserDto user;

    public Review toReview(){
        Review review = new Review();
        review.setText(text);
        review.setRate(rate);
        return review;
    }

    public static ReviewDto fromReview(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setText(review.getText());
        reviewDto.setRate(review.getRate());
        reviewDto.setUser(UserDto.fromUser(review.getUser()));
        return reviewDto;
    }

}
