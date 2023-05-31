package com.technomarket.technomarket.service;

import com.technomarket.technomarket.entity.Cart;
import com.technomarket.technomarket.entity.Product;

import java.security.Principal;

public interface CartService {

    Cart getCart(Principal principal);
    void addToCart(Product product, Principal principal);

    Cart deleteFromCart(Long id, Principal principal);


}
