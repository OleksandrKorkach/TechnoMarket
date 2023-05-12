package com.technomarket.technomarket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.Feedback;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedbackDto {
    private String text;
    private Integer rate;

    public Feedback toFeedback(){
        Feedback feedback = new Feedback();
        feedback.setText(text);
        feedback.setRate(rate);
        return feedback;
    }

}
