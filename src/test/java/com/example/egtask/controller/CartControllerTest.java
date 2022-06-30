package com.example.egtask.controller;

import com.example.egtask.dto.ProductDto;
import com.example.egtask.facade.CartFacade;
import com.example.egtask.model.Cart;
import com.example.egtask.model.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartFacade facade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createCart_ExpectedOkResponseStatusAndCreatedCartInSession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cart"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        HttpSession session = result.getRequest().getSession();
        assertNotNull(session);
        assertNotNull(session.getAttribute("cart"));
    }

    @Test
    public void addProduct_CartIsEmpty_ExpectedOkResponseStatusAndCreatedCartWIthProductInSession() throws Exception {
        Cart cart = new Cart();
        Cart cartWithResult = new Cart();
        cartWithResult.setItems(List.of(CartItem.builder().quantity(1).product(ProductDto.builder().title("test").price(10d).build()).build()));
        when(facade.addProduct(cart, 20L)).thenReturn(cartWithResult);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cart/product/20"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        HttpSession session = result.getRequest().getSession();
        assertNotNull(session);
        assertNotNull(session.getAttribute("cart"));
        assertEquals(cartWithResult, session.getAttribute("cart"));
    }

    @Test
    public void addProduct_CartIsNotEmpty_ExpectedOkResponseStatusAndCartWIthProductInSession() throws Exception {
        Cart cart = new Cart();
        cart.setItems(List.of(CartItem.builder().quantity(1).product(ProductDto.builder().title("test").price(10d).build()).build()));

        Cart cartWithResult = new Cart();
        cartWithResult.setItems(List.of(CartItem.builder().quantity(1).product(ProductDto.builder().title("test").price(10d).build()).build(),
                CartItem.builder().quantity(1).product(ProductDto.builder().title("test22").price(22.2d).build()).build()));

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("cart", cart);

        when(facade.addProduct(cart, 20L)).thenReturn(cartWithResult);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cart/product/20").session(mockHttpSession))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        HttpSession session = result.getRequest().getSession();

        assertNotNull(session);
        assertNotNull(session.getAttribute("cart"));
        assertEquals(cartWithResult, session.getAttribute("cart"));
    }

    @Test
    public void listAll_CartIsNotEmpty_ExpectedOkResponseStatusAndCartItems() throws Exception {
        Cart cartWithResult = new Cart();
        CartItem cartItem = CartItem.builder().quantity(1).product(ProductDto.builder().title("test").price(10d).build()).build();
        CartItem cartItem1 = CartItem.builder().quantity(3).product(ProductDto.builder().title("test3").price(199.d).build()).build();
        cartWithResult.setItems(List.of(cartItem, cartItem1));

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("cart", cartWithResult);

        mvc.perform(MockMvcRequestBuilders.get("/cart/product").session(mockHttpSession))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(cartItem, cartItem1))));

    }
}
