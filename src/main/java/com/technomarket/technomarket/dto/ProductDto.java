package com.technomarket.technomarket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.entity.enums.Category;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private String title;
    private String description;
    private Double price;
    private Boolean in_stock;
    private Integer quantity;


    public Product toProduct(){
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setInStock(in_stock);
        product.setQuantity(quantity);

        return product;
    }
}
