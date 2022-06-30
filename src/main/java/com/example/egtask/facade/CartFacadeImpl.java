package com.example.egtask.facade;

import com.example.egtask.dto.ProductDto;
import com.example.egtask.model.Cart;
import com.example.egtask.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartFacadeImpl implements CartFacade {
    private final ProductFacade productFacade;

    @Autowired
    public CartFacadeImpl(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @Override
    public void removeProduct(Cart cart, Long productId) {
        cart.getItems().stream().filter(x -> x.getProduct().getId().equals(productId)).findFirst().ifPresent(cartItem -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            } else {
                cart.getItems().removeIf(cartItem1 -> cartItem1.equals(cartItem));
            }
        });
    }

    @Override
    public Cart addProduct(Cart cart, Long productId) {
        cart.getItems().stream().filter(x -> x.getProduct().getId().equals(productId)).findFirst().ifPresentOrElse(cartItem -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }, () -> {
            ProductDto productDto = productFacade.getById(productId);
            cart.getItems().add(new CartItem(productDto));
        });

        return cart;
    }
}
