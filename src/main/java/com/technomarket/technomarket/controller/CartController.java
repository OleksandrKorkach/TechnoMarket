package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.products.CartDto;
import com.technomarket.technomarket.entity.Cart;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.service.CartService;
import com.technomarket.technomarket.service.ProductService;
import com.technomarket.technomarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal){
        Cart cart = cartService.getCart(principal);
        CartDto response = CartDto.fromCart(cart);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add/product-{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long productId, Principal principal){
        cartService.addToCart(productService.getProductById(productId), principal);
        return ResponseEntity.ok("successfully added!");
    }

    @PostMapping("/delete/product-{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long productId, Principal principal){
        Cart updatedCart = cartService.deleteFromCart(productId, principal);
        CartDto response = CartDto.fromCart(updatedCart);
        return ResponseEntity.ok(response);
    }
}
