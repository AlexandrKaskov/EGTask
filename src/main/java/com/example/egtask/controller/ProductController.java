package com.example.egtask.controller;

import com.example.egtask.controller.spec.ProductSpec;
import com.example.egtask.dto.ProductDto;
import com.example.egtask.dto.UpdateProductDto;
import com.example.egtask.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductFacade facade;

    @PostMapping
    public ProductDto create(@Valid @RequestBody ProductDto productDto) {
        return facade.create(productDto);
    }

    @GetMapping
    public Page<ProductDto> search(ProductSpec spec, Pageable pageable) {
        return facade.search(spec, pageable);
    }

    @PatchMapping("/{productId}")
    public ProductDto update(@PathVariable Long productId, @RequestBody UpdateProductDto productDto) {
        return facade.update(productId, productDto);
    }

    @DeleteMapping("/{productId}")
    public void delete(@PathVariable Long productId) {
        facade.delete(productId);
    }
}
