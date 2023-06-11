package com.technomarket.technomarket.entity.order;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "delivery_point",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"city", "name"})})
public class DeliveryPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "post_index")
    private Integer postIndex;

}
