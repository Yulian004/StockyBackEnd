package com.generation.stockybackend.model.entities;

import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.enums.OptionType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


	@PrePersist
	public void onCreate()
	{
		time = LocalDateTime.now();
	}
}
