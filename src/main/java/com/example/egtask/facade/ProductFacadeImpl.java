package com.example.egtask.facade;

import com.example.egtask.controller.spec.ProductSpec;
import com.example.egtask.domain.Product;
import com.example.egtask.dto.ProductDto;
import com.example.egtask.dto.UpdateProductDto;
import com.example.egtask.exception.ResourceNotFoundException;
import com.example.egtask.mapper.ProductMapper;
import com.example.egtask.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ProductFacadeImpl implements ProductFacade {
    private final ProductService service;
    private final ProductMapper productMapper;

    @Autowired
    public ProductFacadeImpl(ProductService service, ProductMapper productMapper) {
        this.service = service;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        return productMapper.toProductDto(service.create(product));
    }

    @Override
    public Page<ProductDto> search(ProductSpec spec, Pageable pageable) {
        return service
                .search(spec, pageable)
                .map(productMapper::toProductDto);
    }

    @Override
    public ProductDto update(Long productId, UpdateProductDto productDto) {
        Product product = getByIdInternal(productId);
        productMapper.updateProductWithPatch(productDto, product);
        return productMapper.toProductDto(service.update(product));
    }

    @Override
    public void delete(Long productId) {
        service.delete(productId);
    }

    @Override
    public ProductDto getById(Long productId) {
        return productMapper.toProductDto(getByIdInternal(productId));
    }

    private Product getByIdInternal(Long productId) {
        Optional<Product> productOptional = service.getById(productId);
        return productOptional.orElseThrow(() -> new ResourceNotFoundException("Product not found by ID", productId));
    }
}
