package com.generation.stockybackend.model.repositories;

import com.generation.stockybackend.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
