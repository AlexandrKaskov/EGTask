package com.example.egtask.facade;

import com.example.egtask.dto.ProductDto;
import com.example.egtask.exception.ResourceNotFoundException;
import com.example.egtask.model.Cart;
import com.example.egtask.model.CartItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartFacadeTest {
    @Autowired
    private CartFacade cartFacade;

    @MockBean
    private ProductFacade productFacade;

    @Test
    public void removeProduct_SingleProductInCart_ShouldRemoveProduct() {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>() {{
            add(CartItem.builder().product(ProductDto.builder().id(12L).price(20d).title("TestProduct").build()).quantity(1).build());
        }});
        cartFacade.removeProduct(cart, 12L);
        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void removeProduct_SeveralQuantityProductInCart_ShouldDecreaseCountOfProduct() {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>() {{
            add(CartItem.builder().product(ProductDto.builder().id(12L).price(20d).title("TestProduct").build()).quantity(5).build());
        }});
        cartFacade.removeProduct(cart, 12L);
        assertEquals(1, cart.getItems().size());
        assertEquals(4, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void removeProduct_SeveralProductsWithDiffQuantityProductInCart_ShouldDecreaseCountOfProduct() {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>() {{
            add(CartItem.builder().product(ProductDto.builder().id(12L).price(20d).title("TestProduct").build()).quantity(5).build());
            add(CartItem.builder().product(ProductDto.builder().id(8L).price(10d).title("TestProduct1").build()).quantity(2).build());
        }});
        cartFacade.removeProduct(cart, 8L);
        assertEquals(2, cart.getItems().size());
        assertEquals(1, cart.getItems().get(1).getQuantity());
    }

    @Test
    public void addProduct_ProductNotFound_ShouldThrow() {
        Cart cart = new Cart();
        when(productFacade.getById(10L)).thenThrow(new ResourceNotFoundException("blblla", 10L));

        Assertions.assertThrows(ResourceNotFoundException.class, () -> cartFacade.addProduct(cart, 10L));
    }

    @Test
    public void addProduct_ProductFoundSingleQuantity_ShouldAdd() {
        Cart cart = new Cart();
        ProductDto productDto = ProductDto.builder().title("Test").price(10D).id(10L).build();
        when(productFacade.getById(10L)).thenReturn(productDto);

        Cart cartActual = cartFacade.addProduct(cart, 10L);

        assertEquals(1, cartActual.getItems().size());
        assertEquals(CartItem.builder().product(productDto).quantity(1).build(), cartActual.getItems().get(0));
    }

    @Test
    public void addProduct_ProductFoundNotFirstQuantity_ShouldAdd() {
        Cart cart = new Cart();
        ProductDto productDto = ProductDto.builder().title("Test").price(10D).id(10L).build();
        cart.getItems().add(CartItem.builder().product(productDto).quantity(1).build());
        when(productFacade.getById(10L)).thenReturn(productDto);

        Cart cartActual = cartFacade.addProduct(cart, 10L);

        assertEquals(1, cartActual.getItems().size());
        assertEquals(2, cartActual.getItems().get(0).getQuantity());
    }
}
