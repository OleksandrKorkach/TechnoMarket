package com.technomarket.technomarket.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.enums.OrderStatus;
import com.technomarket.technomarket.entity.order.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSummaryDto {
    private Long id;
    private Double orderPrice;
    private LocalDateTime created;
    private OrderStatus status;

    public static OrderSummaryDto fromOrder(Order order){
        OrderSummaryDto dto = new OrderSummaryDto();
        dto.setId(order.getId());
        dto.setOrderPrice(order.getOrderPrice());
        dto.setCreated(order.getCreated());
        dto.setStatus(order.getStatus());
        return dto;
    }

}
