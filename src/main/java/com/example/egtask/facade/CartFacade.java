package com.example.egtask.facade;

import com.example.egtask.model.Cart;

public interface CartFacade {
    void removeProduct(Cart cart, Long productId);

    Cart addProduct(Cart cart, Long productId);
}
