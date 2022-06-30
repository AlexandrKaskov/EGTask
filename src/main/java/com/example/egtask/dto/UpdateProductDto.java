package com.example.egtask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Double price;
}
