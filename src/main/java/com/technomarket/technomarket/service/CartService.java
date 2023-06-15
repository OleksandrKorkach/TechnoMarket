package com.technomarket.technomarket.service;

import com.technomarket.technomarket.dto.products.CartDto;
import com.technomarket.technomarket.entity.Cart;

import java.security.Principal;

public interface CartService {

    CartDto getCartDtoByPrincipal(Principal principal);

    Cart findCartByPrincipal(Principal principal);

    void addProductToCart(Long productId, Principal principal);

    CartDto deleteProductFromCart(Long id, Principal principal);


}
