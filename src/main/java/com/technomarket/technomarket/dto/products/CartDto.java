package com.technomarket.technomarket.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.Cart;
import com.technomarket.technomarket.entity.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {
    private List<ProductSummaryDto> products;
    private Double totalPrice;

    public static CartDto fromCart(Cart cart){
        CartDto dto = new CartDto();
        dto.setProducts(cart.getProducts().stream()
                .map(ProductSummaryDto::fromProduct)
                .collect(Collectors.toList()));
        Double total = 0.0;
        for (Product product: cart.getProducts()){
            total += product.getPrice();
        }
        dto.setTotalPrice(total);
        return dto;
    }

}
