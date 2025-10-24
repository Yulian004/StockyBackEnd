package com.generation.stockybackend.model.dtos.product;

import com.generation.stockybackend.model.entities.Options;
import com.generation.stockybackend.model.entities.Section;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductInputDto
{

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
