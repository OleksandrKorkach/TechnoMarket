package com.technomarket.technomarket.service;

import com.technomarket.technomarket.dto.order.DeliveryPointDto;
import com.technomarket.technomarket.dto.order.OrderDto;
import com.technomarket.technomarket.dto.order.OrderSummaryDto;
import com.technomarket.technomarket.entity.order.DeliveryPoint;
import com.technomarket.technomarket.entity.order.Order;
import com.technomarket.technomarket.entity.order.OrderCreationRequest;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    void createOrderFromProduct(OrderCreationRequest request);

    void createOrderFromCart(OrderCreationRequest request);

    List<OrderSummaryDto> getOrdersSummaryDtoByPrincipal(Principal principal);

    OrderDto getOrderDtoById(Long orderId, Principal principal);

    List<DeliveryPointDto> getDeliveryPointsDto(String city);


}
