package com.example.egtask.controller;

import com.example.egtask.facade.CartFacade;
import com.example.egtask.model.Cart;
import com.example.egtask.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartFacade cartFacade;

    @Autowired
    public CartController(CartFacade cartFacade) {
        this.cartFacade = cartFacade;
    }

    @PostMapping
    public Cart createCart(HttpSession session) {
        Cart cart = getFromSessionInternal(session);
        session.setAttribute("cart", cart);
        return cart;
    }

    @PostMapping(value = "product/{productId}")
    public Cart addProduct(@PathVariable("productId") Long productId, HttpSession session) {
        Cart cart = getFromSessionInternal(session);
        session.setAttribute("cart", cartFacade.addProduct(cart, productId));
        return cart;
    }

    @DeleteMapping(value = "product/{productId}")
    public void remove(@PathVariable("productId") Long productId, HttpSession session) {
        Cart cart = getFromSessionInternal(session);
        cartFacade.removeProduct(cart, productId);
        session.setAttribute("cart", cart);
    }

    @GetMapping(value = "product")
    public List<CartItem> listAll(HttpSession session) {
        Cart cart = getFromSessionInternal(session);
        return cart.getItems();
    }

    private Cart getFromSessionInternal(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
        }

        return cart;
    }
}
