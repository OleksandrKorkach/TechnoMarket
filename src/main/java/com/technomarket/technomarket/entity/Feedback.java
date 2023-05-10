package com.technomarket.technomarket.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
@Data
public class Feedback extends BaseEntity{

    @Column(name = "text")
    private String text;
    @Column(name = "rate")
    private Integer rate;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;
}
