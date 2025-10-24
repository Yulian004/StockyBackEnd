package com.generation.stockybackend.model.repositories.product;

import com.generation.stockybackend.model.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>
{
}
