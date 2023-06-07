package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.order.OrderDto;
import com.technomarket.technomarket.dto.order.OrderSummaryDto;
import com.technomarket.technomarket.entity.order.Order;
import com.technomarket.technomarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getOrderList(Principal principal){
        List<Order> orders = orderService.getOrdersByPrincipal(principal);
        List<OrderSummaryDto> response = orders.stream()
                .map(OrderSummaryDto::fromOrder)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId, Principal principal){
        Order order = orderService.getOrderById(orderId, principal);
        OrderDto response = OrderDto.fromOrder(order);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/from/product-{id}")
    public ResponseEntity<?> createOrderFromProduct(@PathVariable Long id, Principal principal){
        orderService.createOrderFromProduct(id, principal);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/from/cart")
    public ResponseEntity<?> createOrderFromCart(Principal principal){
        orderService.createOrderFromCart(principal);
        return ResponseEntity.ok().build();
    }

}

