package com.example.egtask.controller;

import com.example.egtask.dto.ProductDto;
import com.example.egtask.facade.ProductFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductFacade facade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void create_ExpectedOkResponseStatusAndProduct() throws Exception {
        Page<ProductDto> resultDto = new PageImpl<>(List.of(buildDto("Test", 100d)));
        given(facade.search(any(), any())).willReturn(resultDto);

        mvc.perform(get("/product?price=100&title=Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(resultDto)));
    }

    private ProductDto buildDto(String title, Double price) {
        return new ProductDto(RandomUtils.nextLong(), title, price);
    }
}
