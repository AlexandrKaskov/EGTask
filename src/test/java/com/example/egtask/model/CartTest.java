package com.example.egtask.model;

import com.example.egtask.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CartTest {

    @Test
    public void getTotal_EmptyList_ShouldReturnZero() {
        Cart cart = new Cart();
        assertEquals(0, cart.getTotal());
    }

    @Test
    public void getTotal_CollectionContains_ShouldReturnSum() {
        Cart cart = new Cart();
        cart.setItems(List.of(CartItem
                        .builder()
                        .quantity(3)
                        .product(ProductDto.builder().title("T").price(10.0d).build())
                        .build(),
                CartItem
                        .builder()
                        .quantity(4)
                        .product(ProductDto.builder().title("T").price(20.0d).build())
                        .build()
        ));
        assertEquals(110, cart.getTotal());
    }
}
