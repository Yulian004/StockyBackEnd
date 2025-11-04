package com.generation.stockybackend.model.entities.products;

import com.generation.stockybackend.model.entities.BaseEntity;
import com.generation.stockybackend.model.entities.Options;
import com.generation.stockybackend.model.entities.Section;
import com.generation.stockybackend.model.enums.StockStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity
{

	private String name;
	private String category;
	private int quantity;
	private int initialQuantity;
	private double unit_price;
	private static final double sogliaMin  = 0.3;
	private static final double sogliaMin2 = 0.1;
	private double weight;
	private double volume;
	private Boolean inStock;
	@ManyToOne(fetch = FetchType.EAGER)
	private Section section;
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Options> options = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	private StockStatus status = StockStatus.OK;

	public void updateQuantity(int amount)
	{
		this.quantity+=amount;
		checkLevel();
	}

	public void checkLevel()
	{
		if(quantity <= 0)
		{
			quantity = 0;
			status = StockStatus.OUT_OF_STOCK;
			return;
		}

		double ratio = (double)quantity/initialQuantity;
		if (ratio <= sogliaMin2)
			status= StockStatus.CRITICAL;
		else if (ratio<= sogliaMin)
			status = StockStatus.LOW;
		else
			status = StockStatus.OK;
	}




}
