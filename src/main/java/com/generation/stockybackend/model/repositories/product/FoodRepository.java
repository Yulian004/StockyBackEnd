package com.generation.stockybackend.model.repositories.product;

import com.generation.stockybackend.model.entities.products.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID>
{
}
