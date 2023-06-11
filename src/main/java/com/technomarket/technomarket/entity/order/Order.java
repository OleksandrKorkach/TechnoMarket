package com.technomarket.technomarket.entity.order;

import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.entity.enums.DeliveryMethod;
import com.technomarket.technomarket.entity.enums.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    @CreatedDate
    private LocalDateTime created;

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_price")
    private Double orderPrice;

    @Column(name = "delivery_point")
    private String deliveryPoint;

    @Column(name = "delivery_method")
    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;


    @PrePersist
    private void init() {
        created = LocalDateTime.now();
    }
}
