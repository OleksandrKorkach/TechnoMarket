package com.technomarket.technomarket.repository;

import com.technomarket.technomarket.entity.product.Product;
import com.technomarket.technomarket.entity.product.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{
    @Query("SELECT p FROM Product p WHERE " +
            "(:#{#filter.minPrice} IS NULL OR p.price > :#{#filter.minPrice}) AND " +
            "(:#{#filter.maxPrice} IS NULL OR p.price < :#{#filter.maxPrice}) AND " +
            "(:#{#filter.inStock} IS NULL OR p.inStock = :#{#filter.inStock}) AND " +
            "(:#{#filter.category} IS NULL OR p.category = :#{#filter.category})")
    Page<Product> findAllByFilter(@Param("filter") ProductFilter filter, Pageable pageable);


}
