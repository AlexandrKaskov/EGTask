package com.example.egtask.model;

import com.example.egtask.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CartItem {
    @JsonProperty("product")
    private ProductDto product;

    @JsonProperty("quantity")
    private Integer quantity;

    public CartItem(ProductDto product) {
        this.product = product;
        this.quantity = 1;
    }
}
