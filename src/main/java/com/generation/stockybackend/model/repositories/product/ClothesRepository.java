package com.generation.stockybackend.model.repositories.product;

import com.generation.stockybackend.model.entities.products.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClothesRepository extends JpaRepository<Clothes, UUID>
{
}
