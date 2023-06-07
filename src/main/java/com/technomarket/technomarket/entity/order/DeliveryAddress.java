package com.technomarket.technomarket.entity.order;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "delivery_address",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"city", "delivery_point"})})
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "delivery_point")
    private String deliveryPoint;

    @Column(name = "address")
    private String address;

    @Column(name = "post_index")
    private Integer postIndex;

}
