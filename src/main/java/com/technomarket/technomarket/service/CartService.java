package com.technomarket.technomarket.service;

import com.technomarket.technomarket.entity.Cart;
import com.technomarket.technomarket.entity.Product;

import java.security.Principal;

public interface CartService {

    Cart getCart(Principal principal);

    void addToCart(Long productId, Principal principal);

    Cart deleteFromCart(Long productId, Principal principal);


}
