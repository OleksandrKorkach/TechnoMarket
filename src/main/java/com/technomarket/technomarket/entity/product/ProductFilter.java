package com.technomarket.technomarket.entity.product;

import com.technomarket.technomarket.entity.enums.Category;
import lombok.Data;

@Data
public class ProductFilter {
    private Integer pageNumber;
    private Boolean isAscending;
    private Double minPrice;
    private Double maxPrice;
    private Boolean inStock;
    private Category category;

    public ProductFilter(Integer pageNumber, Boolean isAscending, Double minPrice, Double maxPrice, Boolean inStock, Category category) {
        this.pageNumber = pageNumber;
        this.isAscending = isAscending;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.inStock = inStock;
        this.category = category;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Boolean getAscending() {
        return isAscending;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public Category getCategory() {
        return category;
    }

}
