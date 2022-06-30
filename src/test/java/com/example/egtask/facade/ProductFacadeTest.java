package com.example.egtask.facade;

import com.example.egtask.domain.Product;
import com.example.egtask.dto.ProductDto;
import com.example.egtask.mapper.ProductMapper;
import com.example.egtask.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductFacadeTest {

    @Autowired
    private ProductFacade facade;

    @MockBean
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    private Product product;
    private Product newProduct;

    @BeforeEach
    public void setUp() {
        product = Product.builder().id(10L).price(299.99d).title("Glasses").build();
        newProduct = Product.builder().price(299.99d).title("Glasses").build();
    }

    @Test
    public void create_ShouldCallCreate() {
        when(service.create(newProduct)).thenReturn(product);
        facade.create(ProductDto.builder().price(299.99d).title("Glasses").build());
        verify(service, atMostOnce()).create(newProduct);
    }
}
