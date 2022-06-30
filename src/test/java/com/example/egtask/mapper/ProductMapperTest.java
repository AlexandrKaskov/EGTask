package com.example.egtask.mapper;

import com.example.egtask.domain.Product;
import com.example.egtask.dto.UpdateProductDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductMapperTest {
    @Autowired
    private ProductMapper mapper;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void updateProductWithPatch_TitleOnlyPresent_ShouldChangeOnlyTitle() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setTitle("Test1");
        Product product = new Product();
        product.setTitle("Title");
        product.setPrice(100d);

        mapper.updateProductWithPatch(updateProductDto, product);
        assertEquals(updateProductDto.getTitle(), product.getTitle());
        assertEquals(100d, product.getPrice());
    }

    @Test
    public void updateProductWithPatch_PriceOnlyPresent_ShouldChangeOnlyPrice() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setPrice(299.22d);
        Product product = new Product();
        product.setTitle("Title");
        product.setPrice(100d);

        mapper.updateProductWithPatch(updateProductDto, product);
        assertEquals("Title", product.getTitle());
        assertEquals(updateProductDto.getPrice(), product.getPrice());
    }

    @Test
    public void updateProductWithPatch_AllValuesArePresent_ShouldChangeAll() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setPrice(299.22d);
        updateProductDto.setTitle("Test111");
        Product product = new Product();
        product.setTitle("Title");
        product.setPrice(100d);

        mapper.updateProductWithPatch(updateProductDto, product);
        assertEquals(updateProductDto.getTitle(), product.getTitle());
        assertEquals(updateProductDto.getPrice(), product.getPrice());
    }

    @Test
    public void updateProductWithPatch_EmptyUpdating_ShouldNotChangeAll() {
        UpdateProductDto updateProductDto = new UpdateProductDto();
        Product product = new Product();
        product.setTitle("Title");
        product.setPrice(100d);

        mapper.updateProductWithPatch(updateProductDto, product);
        assertEquals("Title", product.getTitle());
        assertEquals(100d, product.getPrice());
    }
}
