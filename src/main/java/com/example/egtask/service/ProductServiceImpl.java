package com.example.egtask.service;

import com.example.egtask.domain.Product;
import com.example.egtask.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> search(Specification<Product> spec, Pageable pageable) {
        return productRepository.findAll(spec, pageable);
    }

    @Override
    public Product create(Product product) {
        log.info("Creating new Product: {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        log.info("Updating Product: {}", product);
        return productRepository.save(product);
    }

    @Override
    public void delete(long productId) {
        getById(productId)
                .ifPresentOrElse(product -> {
                            log.info("Deleting Product: {}", product);
                            productRepository.delete(product);
                        }, () -> log.warn("Product not found by ID: {}", productId)
                );
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }
}
