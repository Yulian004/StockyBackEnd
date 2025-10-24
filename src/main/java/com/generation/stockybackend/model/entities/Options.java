package com.generation.stockybackend.model.entities;

import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.enums.OptionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Options extends BaseEntity
{
	private LocalDateTime time;
	private int quantityChange;
	@Enumerated(EnumType.STRING)
	private OptionType type;

	@ManyToOne(fetch = FetchType.EAGER)
	private Product product;

	@PrePersist
	public void onCreate()
	{
		time = LocalDateTime.now();
	}
}
