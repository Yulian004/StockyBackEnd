package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class OptionsInputDto {
    private LocalTime time;
}
