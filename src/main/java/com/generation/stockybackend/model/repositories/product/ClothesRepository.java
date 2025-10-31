package com.generation.stockybackend.model.repositories.product;

import com.generation.stockybackend.model.entities.products.Clothes;
import com.generation.stockybackend.model.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClothesRepository extends JpaRepository<Clothes, UUID>
{
	List<Clothes> findClothesBySize(Size size);

	List<Clothes> findClothesByBrand(String brand);
}
