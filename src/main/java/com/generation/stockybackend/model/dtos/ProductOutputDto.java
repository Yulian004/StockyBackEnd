package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductOutputDto {
    private UUID id;
    private double weight;
    private double volume;
    private int quantity;
}
