package com.example.egtask.service;

import com.example.egtask.domain.Product;
import com.example.egtask.repository.ProductRepository;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.utils.Converter;
import net.kaczmarzyk.spring.data.jpa.utils.QueryContext;
import net.kaczmarzyk.spring.data.jpa.web.annotation.OnTypeMismatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.support.DefaultFormattingConversionService;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(ProductServiceImpl.class)
@DataJpaTest
public class ProductServiceTest {
    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    @Test
    public void create_ShouldCreateNew() {
        Product actual = service.create(Product.builder().title("New").price(20d).build());

        Product expected = repository.findById(actual.getId()).orElse(null);
        assertNotNull(expected);
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    public void update_ShouldUpdate() {
        Product inner = repository.save(Product.builder().title("New").price(20d).build());

        inner.setPrice(99d);
        Product actual = service.update(inner);

        Product expected = repository.findById(actual.getId()).orElse(null);
        assertNotNull(expected);
        assertEquals(99d, actual.getPrice());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    public void delete_ExistsAlready_ShouldDelete() {
        Product inner = repository.save(Product.builder().title("New").price(20d).build());
        service.delete(inner.getId());
        assertTrue(repository.findById(inner.getId()).isEmpty());
    }

    @Nested
    class NestedServiceTest {
        protected QueryContext queryCtx = new QueryContext() {
            @Override
            public Join<?, ?> getEvaluated(String s, Root<?> root) {
                return null;
            }

            @Override
            public void putLazyVal(String s, Function<Root<?>, Join<?, ?>> function) {
            }

            @Override
            public Fetch<?, ?> getEvaluatedJoinFetch(String s) {
                return null;
            }

            @Override
            public void putEvaluatedJoinFetch(String s, Fetch<?, ?> fetch) {
            }
        };
        private Product product = Product.builder().title("Nested").price(11d).build();
        private Product product1 = Product.builder().title("NesEa").price(99d).build();
        private Product product2 = Product.builder().title("Second").price(10291.22d).build();
        private Product product3 = Product.builder().title("Third").price(999.99d).build();

        @BeforeEach
        void setUp() {
            repository.saveAll(
                    List.of(
                            product, product1, product2, product3
                    )
            );
        }

        @Test
        public void search_ShouldFindByTitle() {
            PageRequest pageRequest = PageRequest.of(0, 2);
            Equal<Product> productEqual = new Equal<>(queryCtx, "title", new String[]{"Nested"},
                    Converter.withDateFormat("yyyy-MM-dd", OnTypeMismatch.DEFAULT, new DefaultFormattingConversionService()));

            Page<Product> result = service.search(productEqual, pageRequest);
            assertThat(result).hasSize(1).containsOnly(product);
        }

        @Test
        public void search_ShouldFindByPrice() {
            PageRequest pageRequest = PageRequest.of(0, 2);
            Equal<Product> productEqual = new Equal<>(queryCtx, "price", new String[]{"10291.22"},
                    Converter.withDateFormat("yyyy-MM-dd", OnTypeMismatch.DEFAULT, new DefaultFormattingConversionService()));

            Page<Product> result = service.search(productEqual, pageRequest);
            assertThat(result).hasSize(1).containsOnly(product2);
        }

        @Test
        public void search_PageCountLimit_ShouldFindFirstTwo() {
            PageRequest pageRequest = PageRequest.of(0, 2);
            LikeIgnoreCase<Product> query = new LikeIgnoreCase<>(queryCtx, "title", new String[]{"nes"});
            Page<Product> result = service.search(query, pageRequest);
            assertThat(result).hasSize(2).containsOnly(product, product1);
        }

        @Test
        public void search_ShouldFindAll() {
            PageRequest pageRequest = PageRequest.of(0, 4);
            Page<Product> result = service.search(null, pageRequest);
            assertThat(result).hasSize(4).containsOnly(product, product1, product2, product3);
        }
    }


}
