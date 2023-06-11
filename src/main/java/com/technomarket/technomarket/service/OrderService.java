package com.technomarket.technomarket.service;

import com.technomarket.technomarket.entity.order.DeliveryPoint;
import com.technomarket.technomarket.entity.order.Order;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    void createOrderFromProduct(Long productId, Principal principal);

    void createOrderFromCart(Principal principal);

    List<Order> getOrdersByPrincipal(Principal principal);

    Order getOrderById(Long orderId, Principal principal);

    List<DeliveryPoint> getDeliveryPoints(String city);

}
