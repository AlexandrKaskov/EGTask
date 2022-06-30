package com.example.egtask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    @NotBlank
    private String title;

    @JsonProperty("price")
    @NotBlank
    private Double price;
}
