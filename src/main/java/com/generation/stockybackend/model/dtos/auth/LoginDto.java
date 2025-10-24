package com.generation.stockybackend.model.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto
{
    private String email;
    private String password;
}
