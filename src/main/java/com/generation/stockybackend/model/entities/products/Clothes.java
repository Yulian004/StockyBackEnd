package com.generation.stockybackend.model.entities.products;

import com.generation.stockybackend.model.enums.Size;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Clothes extends Product
{
	private String brand;
	private String material;
	private String color;
	private Size size;


}
