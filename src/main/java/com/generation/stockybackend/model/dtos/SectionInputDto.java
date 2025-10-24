package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionInputDto {
    private String category;
    private String room;
    private int number;
    private double maxWeight;
    private double maxVolume;
}
