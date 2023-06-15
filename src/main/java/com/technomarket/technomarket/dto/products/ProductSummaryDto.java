package com.technomarket.technomarket.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.product.Product;
import com.technomarket.technomarket.entity.enums.Category;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSummaryDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    private Category category;

    public static ProductSummaryDto fromProduct(Product product){
        ProductSummaryDto dto = new ProductSummaryDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
