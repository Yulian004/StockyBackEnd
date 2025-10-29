package com.generation.stockybackend.model.dtos.product;

import com.generation.stockybackend.model.entities.Options;
import com.generation.stockybackend.model.entities.Section;
import com.generation.stockybackend.model.enums.Cold_Food;
import com.generation.stockybackend.model.enums.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductInputDto
{

	private String name;
	private String category;
	private int quantity;
	private int unit_price;
	private double weight;
	private double volume;
	private UUID sectionId;
	private String type;
	private String energy_class;
	private int warranty_months;
	private LocalDate expire_date;
	private boolean bio;
	private Cold_Food cold;
	private String brand;
	private String material;
	private String color;
	private Size size;

}
