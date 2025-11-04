package com.generation.stockybackend.model.dtos.options;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class OperationInTimeDto
{
	private String email;
	private LocalDate from;
	private LocalDate to;
}
