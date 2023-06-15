package com.technomarket.technomarket.entity.product;

import com.technomarket.technomarket.entity.BaseEntity;
import com.technomarket.technomarket.entity.Review;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.entity.enums.Category;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;

    @LastModifiedDate
    @Column(name = "updated")
    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "in_stock")
    private Boolean inStock;
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Review> reviews;


    @Override
    public String toString() {
        return "Product{" +
                "id: " + super.getId() + ", " +
                "title: " +  title + ", " +
                "description: " + description + ", " +
                "price: " + price + ", " +
                "quantity: " + quantity + ", " +
                "username: " + user.getUsername() + "}";
    }
}
