package com.example.egtask.facade;

import com.example.egtask.controller.spec.ProductSpec;
import com.example.egtask.dto.ProductDto;
import com.example.egtask.dto.UpdateProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductFacade {

    ProductDto create(ProductDto productDto);

    Page<ProductDto> search(ProductSpec spec, Pageable pageable);

    ProductDto getById(Long productId);

    ProductDto update(Long productId, UpdateProductDto productDto);

    void delete(Long productId);
}
