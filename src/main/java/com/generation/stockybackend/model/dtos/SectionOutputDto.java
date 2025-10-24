package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SectionOutputDto {
    private UUID id;
    private String category;
    private String room;
    private int number;
    private double maxWeight;
    private double maxVolume;
}
