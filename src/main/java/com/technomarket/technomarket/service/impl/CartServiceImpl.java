package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.dto.products.CartDto;
import com.technomarket.technomarket.entity.Cart;
import com.technomarket.technomarket.entity.product.Product;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.repository.CartRepository;
import com.technomarket.technomarket.service.CartService;
import com.technomarket.technomarket.service.ProductService;
import com.technomarket.technomarket.service.UserService;
import com.technomarket.technomarket.service.impl.exceptions.ContentAlreadyExistException;
import com.technomarket.technomarket.service.impl.exceptions.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final CartRepository cartRepository;
    private final ProductService productService;

    @Autowired
    public CartServiceImpl(UserService userService, CartRepository cartRepository, ProductService productService) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public CartDto getCartDtoByPrincipal(Principal principal) {
        Cart cart =  findCartByPrincipal(principal);
        return CartDto.fromCart(cart);
    }

    public Cart findCartByPrincipal(Principal principal){
        if (principal == null){
            throw new UnauthorizedAccessException("User is not authorized!");
        }
        Optional<Cart> cart = cartRepository.findCartByUser(userService.findUserByUsername(principal.getName()));
        return cart.orElseGet(() -> getEmptyCart(principal));
    }

    public Cart getEmptyCart(Principal principal){
        Cart cart = new Cart();
        cart.setProducts(new ArrayList<>());
        cart.setUser(userService.findUserByPrincipal(principal));
        return cart;
    }

    @Override
    public void addProductToCart(Long productId, Principal principal){
        Cart cart = findCartByPrincipal(principal);
        Product product = productService.findProductById(productId);
        if (cart.getProducts().stream()
                .anyMatch(productFromCart -> productFromCart.getId().equals(productId))){
            throw new ContentAlreadyExistException("Product is already in cart");
        }
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    @Override
    public CartDto deleteProductFromCart(Long id, Principal principal){
        Cart cart = findCartByPrincipal(principal);
        cart.getProducts().removeIf(product -> product.getId().equals(id));
        cartRepository.deleteById(cart.getId());
        cartRepository.save(cart);
        return CartDto.fromCart(cart);
    }


}
