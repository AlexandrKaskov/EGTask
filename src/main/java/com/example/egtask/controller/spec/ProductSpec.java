package com.example.egtask.controller.spec;

import com.example.egtask.domain.Product;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "title", params = "title", spec = LikeIgnoreCase.class),
        @Spec(path = "price", params = {"price"}, spec = Equal.class)
})
public interface ProductSpec extends Specification<Product> {
}
