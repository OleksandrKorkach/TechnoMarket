package com.technomarket.technomarket.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.order.DeliveryPoint;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryPointDto {
    private Long id;
    private String name;
    private String city;
    private String address;
    private Integer postIndex;

    public static DeliveryPointDto fromDeliveryPoint(DeliveryPoint deliveryPoint){
        DeliveryPointDto dto = new DeliveryPointDto();
        dto.setId(deliveryPoint.getId());
        dto.setName(deliveryPoint.getName());
        dto.setCity(deliveryPoint.getCity());
        dto.setAddress(deliveryPoint.getAddress());
        dto.setPostIndex(deliveryPoint.getPostIndex());
        return dto;
    }

}
