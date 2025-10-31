package com.generation.stockybackend.model.entities.products;

import com.generation.stockybackend.model.enums.Cold_Food;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Food extends Product
{

	@FutureOrPresent
	private LocalDate expire_date;
	private boolean bio;
	@Enumerated(EnumType.STRING)
	private Cold_Food cold;
}
