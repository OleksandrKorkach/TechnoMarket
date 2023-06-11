package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.entity.Product;
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
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public void createOrderFromProduct(Long productId, Principal principal) {
        Order order = new Order();
        List<Product> products = new ArrayList<>();
        Product product = productService.getProductById(productId);
        products.add(product);
        order.setProducts(products);
        order.setUser(userService.getUserByPrincipal(principal));
        order.setOrderPrice(product.getPrice());
        order.setStatus(OrderStatus.WAITING_FOR_DELIVERY);
        order.setDeliveryMethod(DeliveryMethod.IN_DELIVERY_POINT);
        order.setDeliveryPoint("Cool delivery point");
        orderRepository.save(order);
    }

    @Override
    public void createOrderFromCart(Principal principal) {
        Order order = new Order();
        List<Product> products = new ArrayList<>();
        products.addAll(cartService.getCart(principal).getProducts());
        order.setProducts(products);
        order.setUser(userService.getUserByPrincipal(principal));
        Double orderPrice = 0.0;
        for (Product product : products){
            orderPrice += product.getPrice();
        }
        order.setOrderPrice(orderPrice);
        order.setStatus(OrderStatus.WAITING_FOR_DELIVERY);
        order.setDeliveryMethod(DeliveryMethod.IN_DELIVERY_POINT);
        order.setDeliveryPoint("Very big cool delivery point");
        System.out.println(order);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByPrincipal(Principal principal){
        String principalName = principal.getName();
        List<Order> orders = new ArrayList<>();
        for (Order order: orderRepository.findAll()){
            if (order.getUser().getUsername().equals(principalName)){
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public Order getOrderById(Long orderId, Principal principal){
        String principalName = principal.getName();
        Order order = orderRepository.getById(orderId);
        if (order.getUser().getUsername().equals(principalName)){
            return order;
        } else {
            throw new UnauthorizedAccessException("You don't have enough authorities to get this order");
        }
    }

    @Override
    public List<DeliveryPoint> getDeliveryPoints(String city) {
        if (city == null){
            return deliveryPointRepository.findAll();
        } else {
            return deliveryPointRepository.findDeliveryPointByCity(city);
        }

    }

}
