package com.generation.stockybackend.model.dtos.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterDto
{
    private String name;
    private String surname;
    private String email;
    private String role;
    private String password;
    private LocalDate registrationDate;
}
