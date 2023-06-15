package com.technomarket.technomarket.entity.order;

import lombok.Data;

import java.security.Principal;

@Data
public class OrderCreationRequest {
    private String DeliveryMethod;
    private Long DeliveryPointId;
    private Principal principal;
    private Long productId;


}
