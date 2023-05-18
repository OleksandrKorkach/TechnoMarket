package com.technomarket.technomarket.dto.reviews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.Review;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDto extends ReviewContentDto {

    private String username;
    public Review toReview(){
        Review review = new Review();
        review.setText(super.getText());
        review.setRate(super.getRate());
        return review;
    }

    public static ReviewDto fromReview(Review review){
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setText(review.getText());
        dto.setRate(review.getRate());
        dto.setUsername(review.getUser().getUsername());
        return dto;
    }

}
