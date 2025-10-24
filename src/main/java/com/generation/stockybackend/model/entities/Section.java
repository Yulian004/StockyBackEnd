package com.generation.stockybackend.model.entities;

import com.generation.stockybackend.model.entities.products.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
public class Section extends BaseEntity
{

	@OneToMany(mappedBy = "section", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet<>();

	private void addProduct(Product product)
	{
		products.add(product);
		product.setSection(this);
	}
}
