package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.products.CartDto;
import com.technomarket.technomarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal){
        CartDto cart = cartService.getCartDtoByPrincipal(principal);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add/product-{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long productId, Principal principal){
        cartService.addProductToCart(productId, principal);
        return ResponseEntity.ok("successfully added!");
    }

    @PostMapping("/delete/product-{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long productId, Principal principal){
        CartDto updatedCart = cartService.deleteProductFromCart(productId, principal);
        return ResponseEntity.ok(updatedCart);
    }
}
