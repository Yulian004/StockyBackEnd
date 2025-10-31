package com.generation.stockybackend.model.dtos.options;

import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.enums.OptionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class OptionsOutputDto {
    private UUID id;
    private LocalDateTime time;
    private OptionType type;
    private int quantityChange;
    private String product;
    private String user;

}
