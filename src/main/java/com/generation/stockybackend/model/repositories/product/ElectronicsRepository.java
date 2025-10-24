package com.generation.stockybackend.model.repositories.product;

import com.generation.stockybackend.model.entities.products.Electronics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElectronicsRepository extends JpaRepository<Electronics, UUID>
{
}
