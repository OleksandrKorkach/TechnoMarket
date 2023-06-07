package com.technomarket.technomarket.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.dto.products.ProductSummaryDto;
import com.technomarket.technomarket.entity.enums.DeliveryMethod;
import com.technomarket.technomarket.entity.enums.OrderStatus;
import com.technomarket.technomarket.entity.order.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    private Long id;
    private LocalDateTime created;
    private List<ProductSummaryDto> products;
    private OrderStatus status;
    private Double orderPrice;
    private String deliveryPoint;
    private DeliveryMethod deliveryMethod;

    public static OrderDto fromOrder(Order order){
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCreated(order.getCreated());
        dto.setProducts(order.getProducts().stream()
                .map(ProductSummaryDto::fromProduct)
                .collect(Collectors.toList()));
        dto.setStatus(order.getStatus());
        dto.setOrderPrice(order.getOrderPrice());
        dto.setDeliveryPoint(order.getDeliveryPoint());
        dto.setDeliveryMethod(order.getDeliveryMethod());
        return dto;
    }

}
