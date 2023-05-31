package com.technomarket.technomarket.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.Cart;
import lombok.Data;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {
    private List<ProductSummaryDto> products;

    public static CartDto fromCart(Cart cart){
        CartDto dto = new CartDto();
        dto.setProducts(cart.getProducts().stream()
                .map(ProductSummaryDto::fromProduct)
                .collect(Collectors.toList()));
        return dto;
    }

}
