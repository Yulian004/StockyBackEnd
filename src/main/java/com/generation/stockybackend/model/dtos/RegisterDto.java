package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterDto
{
    private String name;
    private String surname;
    private String email;
    private String role;
    private String password;
}
