package com.technomarket.technomarket.repository;

import com.technomarket.technomarket.entity.Cart;
import com.technomarket.technomarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUser(User user);

}
