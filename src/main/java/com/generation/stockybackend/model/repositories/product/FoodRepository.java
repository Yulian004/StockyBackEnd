package com.generation.stockybackend.model.repositories.product;

import com.generation.stockybackend.model.entities.products.Food;
import com.generation.stockybackend.model.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID>
{
	List<Food> findFoodByExpire_date(LocalDate date);
}
