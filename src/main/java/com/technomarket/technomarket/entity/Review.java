package com.technomarket.technomarket.entity;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Data
public class Review extends BaseEntity{

    @Column(name = "text")
    private String text;
    @Column(name = "rate")
    private Integer rate;

    @LastModifiedDate
    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Review{" +
                "id: " + super.getId() + ", " +
                "text: " + text + ", " +
                "rate: " + rate + "}";
    }
}
