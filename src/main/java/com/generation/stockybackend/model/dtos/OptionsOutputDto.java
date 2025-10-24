package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class OptionsOutputDto {
    private UUID id;
    private LocalTime time;
}
