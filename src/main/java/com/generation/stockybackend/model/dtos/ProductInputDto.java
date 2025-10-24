package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInputDto {
    private double weight;
    private double volume;
    private int quantity;
}
