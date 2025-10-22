package com.generation.stockybackend.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserOutputDto
{
    private String email;
    private Set<String> roles;
    private String name;
    private String surname;
}
