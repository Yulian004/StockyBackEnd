package com.generation.stockybackend.model.dtos.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserOutputDto
{
    private String email;
    private String role;
    private String name;
    private String surname;
     private LocalDate registrationDate;
}
