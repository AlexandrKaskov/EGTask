package com.example.egtask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Cart {
    @JsonProperty("total")
    @Setter(AccessLevel.NONE)
    private Double total;

    @JsonProperty("items")
    private List<CartItem> items = new ArrayList<>();

    public Double getTotal() {
        return items
                .stream()
                .filter(x -> x.getProduct() != null)
                .map(x -> x.getProduct().getPrice() * x.getQuantity())
                .collect(Collectors.summarizingDouble(Double::doubleValue))
                .getSum();
    }
}
