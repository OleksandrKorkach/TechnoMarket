package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.entity.Cart;
import com.technomarket.technomarket.entity.Product;
import com.technomarket.technomarket.repository.CartRepository;
import com.technomarket.technomarket.service.CartService;
import com.technomarket.technomarket.service.UserService;
import com.technomarket.technomarket.service.impl.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final CartRepository cartRepository;
    private final ProductServiceImpl productService;

    @Autowired
    public CartServiceImpl(UserService userService, CartRepository cartRepository, ProductServiceImpl productService) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }


    @Override
    public Cart getCart(Principal principal) {
        Optional<Cart> cartFromDb = cartRepository.findCartByUser(userService.findByUsername(principal.getName()));

        if (cartFromDb.isPresent()){
            return cartFromDb.get();
        } else {
            Cart cart = new Cart();
            cart.setProducts(new ArrayList<>());
            cart.setUser(userService.findByUsername(principal.getName()));
            return cart;
        }
    }

    @Override
    public void addToCart(Product product, Principal principal){
        Optional<Cart> cartFromDb = cartRepository.findCartByUser(userService.findByUsername(principal.getName()));
        Cart cart;
        if (cartFromDb.isPresent()){
            cart = cartFromDb.get();
            List<Product> productList = cart.getProducts();
            productList.add(product);
            cart.setProducts(productList);
            cartRepository.save(cart);
        } else {
            cart = new Cart();
            cart.setProducts(new ArrayList<>());
            cart.setUser(userService.findByUsername(principal.getName()));
            cart.getProducts().add(product);
            cartRepository.save(cart);
        }
    }

    @Override
    public Cart deleteFromCart(Long id, Principal principal){
        Optional<Cart> cartFromDb = cartRepository.findCartByUser(userService.findByUsername(principal.getName()));
        if (cartFromDb.isPresent()){
            Cart cart = cartFromDb.get();
            List<Product> products = cart.getProducts();
            products.removeIf(product -> product.getId().equals(id));
            cart.setProducts(products);
            cartRepository.deleteById(cartFromDb.get().getId());
            cartRepository.save(cart);
            return cart;
        } else {
            throw new ResourceNotFoundException("Not found product with such id");
        }

    }
}
