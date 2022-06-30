package com.example.egtask.service;

import com.example.egtask.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface ProductService {
    Page<Product> search(Specification<Product> spec, Pageable pageable);

    Product create(Product product);

    Product update(Product product);

    void delete(long productId);

    Optional<Product> getById(Long id);
}
