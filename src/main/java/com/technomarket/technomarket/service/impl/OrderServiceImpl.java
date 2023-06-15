package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.dto.order.DeliveryPointDto;
import com.technomarket.technomarket.dto.order.OrderDto;
import com.technomarket.technomarket.dto.order.OrderSummaryDto;
import com.technomarket.technomarket.entity.order.OrderCreationRequest;
import com.technomarket.technomarket.entity.product.Product;
import com.technomarket.technomarket.entity.enums.DeliveryMethod;
import com.technomarket.technomarket.entity.enums.OrderStatus;
import com.technomarket.technomarket.entity.order.DeliveryPoint;
import com.technomarket.technomarket.entity.order.Order;
import com.technomarket.technomarket.repository.DeliveryPointRepository;
import com.technomarket.technomarket.repository.OrderRepository;
import com.technomarket.technomarket.service.CartService;
import com.technomarket.technomarket.service.OrderService;
import com.technomarket.technomarket.service.ProductService;
import com.technomarket.technomarket.service.UserService;
import com.technomarket.technomarket.service.impl.exceptions.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DeliveryPointRepository deliveryPointRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, UserService userService, CartService cartService, DeliveryPointRepository deliveryPointRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.deliveryPointRepository = deliveryPointRepository;
    }

    @Override
    public void createOrderFromProduct(OrderCreationRequest request) {
        Order order = new Order();
        Principal principal = userService.getPrincipal();
        request.setPrincipal(principal);
        setOrderProducts(order, request);
        setCommonOrderDetails(order, request);
        orderRepository.save(order);
    }

    @Override
    public void createOrderFromCart(OrderCreationRequest request) {
        Order order = new Order();
        Principal principal = userService.getPrincipal();
        request.setPrincipal(principal);
        setOrderProducts(order, request);
        setCommonOrderDetails(order, request);
        orderRepository.save(order);
    }

    public void setOrderProducts(Order order, OrderCreationRequest request){
        List<Product> products = new ArrayList<>();
        if (request.getProductId() == null){
            products.addAll(cartService.findCartByPrincipal(request.getPrincipal()).getProducts());
        } else {
            products.add(productService.findProductById(request.getProductId()));
        }
        order.setProducts(products);
    }

    public void setCommonOrderDetails(Order order, OrderCreationRequest request) {
        order.setUser(userService.findUserByPrincipal(request.getPrincipal()));
        Double orderPrice = order.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
        order.setOrderPrice(orderPrice);
        order.setStatus(OrderStatus.WAITING_FOR_DELIVERY);
        order.setDeliveryMethod(DeliveryMethod.valueOf(request.getDeliveryMethod().toUpperCase()));
        if (order.getDeliveryMethod().equals(DeliveryMethod.IN_DELIVERY_POINT)) {
            order.setDeliveryPoint(deliveryPointRepository.findById(request.getDeliveryPointId()).get().getName());
        } else order.setDeliveryPoint("Self-delivery from shop");
    }

    @Override
    public List<OrderSummaryDto> getOrdersSummaryDtoByPrincipal(Principal principal) {
        List<Order> orders = findOrdersByPrincipal(principal);
        return orders.stream()
                .map(OrderSummaryDto::fromOrder)
                .collect(Collectors.toList());
    }

    public List<Order> findOrdersByPrincipal(Principal principal) {
        String principalName = principal.getName();
        List<Order> orders = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            if (order.getUser().getUsername().equals(principalName)) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public OrderDto getOrderDtoById(Long orderId, Principal principal) {
        Order order = findOrderById(orderId, principal);
        return OrderDto.fromOrder(order);
    }

    public Order findOrderById(Long orderId, Principal principal) {
        Order order = orderRepository.getById(orderId);
        if (order.getUser().getUsername().equals(principal.getName())) {
            return order;
        } else {
            throw new UnauthorizedAccessException("You don't have enough authorities to get this order");
        }
    }

    @Override
    public List<DeliveryPointDto> getDeliveryPointsDto(String city){
        List<DeliveryPoint> points = findDeliveryPoints(city);
        return points.stream().
                map(DeliveryPointDto::fromDeliveryPoint)
                .collect(Collectors.toList());
    }

    public List<DeliveryPoint> findDeliveryPoints(String city) {
        if (city == null) {
            return deliveryPointRepository.findAll();
        } else {
            return deliveryPointRepository.findDeliveryPointByCity(city);
        }

    }

}
