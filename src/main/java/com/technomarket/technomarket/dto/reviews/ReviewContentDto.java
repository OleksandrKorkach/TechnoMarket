package com.technomarket.technomarket.dto.reviews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.Review;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewContentDto {
    private Long id;
    private String text;
    private Integer rate;

    public static ReviewContentDto fromReview(Review review){
        ReviewContentDto dto = new ReviewContentDto();
        dto.setId(review.getId());
        dto.setText(review.getText());
        dto.setRate(review.getRate());
        return dto;
    }
}
