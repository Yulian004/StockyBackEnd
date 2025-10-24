package com.generation.stockybackend.model.entities.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Electronics extends Product
{
	private int warranty_months;
	private  String energy_class;
}
