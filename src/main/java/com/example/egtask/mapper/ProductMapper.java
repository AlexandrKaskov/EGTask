package com.example.egtask.mapper;

import com.example.egtask.domain.Product;
import com.example.egtask.dto.ProductDto;
import com.example.egtask.dto.UpdateProductDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class ProductMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void updateProductWithPatch(UpdateProductDto updateProductDto, Product product) {
        if (StringUtils.hasLength(updateProductDto.getTitle())) {
            product.setTitle(updateProductDto.getTitle());
        }
        if (updateProductDto.getPrice() != null) {
            product.setPrice(updateProductDto.getPrice());
        }
    }

    public ProductDto toProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public Product toProduct(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }


}
