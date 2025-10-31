package com.generation.stockybackend.model.dtos.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserOutputDto
{
    private UUID id;
    private String email;
    private String role;
    private String name;
    private String surname;
     private LocalDate registrationDate;
}
