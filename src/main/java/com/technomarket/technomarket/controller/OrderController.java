package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.order.DeliveryPointDto;
import com.technomarket.technomarket.dto.order.OrderDto;
import com.technomarket.technomarket.dto.order.OrderSummaryDto;
import com.technomarket.technomarket.entity.order.DeliveryPoint;
import com.technomarket.technomarket.entity.order.OrderCreationRequest;
import com.technomarket.technomarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryDto>> getOrderList(Principal principal) {
        List<OrderSummaryDto> orders = orderService.getOrdersSummaryDtoByPrincipal(principal);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId, Principal principal) {
        OrderDto order = orderService.getOrderDtoById(orderId, principal);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/create/from/product-{productId}")
    public ResponseEntity<?> createOrderFromProduct(@PathVariable Long productId,
                                                    @ModelAttribute("request") OrderCreationRequest request) {
        request.setProductId(productId);
        orderService.createOrderFromProduct(request);
        return ResponseEntity.ok("successfully created order from product: " + productId);
    }

    @PostMapping("/create/from/cart")
    public ResponseEntity<?> createOrderFromCart(@ModelAttribute("request") OrderCreationRequest request) {
        orderService.createOrderFromCart(request);
        return ResponseEntity.ok("Successfully created order from cart!");
    }

    @GetMapping("/get/delivery_points")
    public ResponseEntity<List<DeliveryPointDto>> getDeliveryPoints(
            @RequestParam(value = "city", required = false) String city) {
        List<DeliveryPointDto> deliveryPoints = orderService.getDeliveryPointsDto(city);
        return ResponseEntity.ok(deliveryPoints);
    }

}

