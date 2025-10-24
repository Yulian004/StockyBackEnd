package com.generation.stockybackend.model.dtos.product;

import com.generation.stockybackend.model.entities.Options;
import com.generation.stockybackend.model.entities.Section;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductOutputDto
{
	private UUID id;
	private String name;
	private String category;
	private int quantity;
	private int unit_price;
	private static final double sogliaMin  = 0.3;
	private static final double sogliaMin2 = 0.1;
	private double weight;
	private double volume;
	private Boolean inStock;
	private Section section;
	private List<Options> options = new ArrayList<>();
}
