package com.technomarket.technomarket.repository;

import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAllByCategory(Category category, Pageable pageable);
}
