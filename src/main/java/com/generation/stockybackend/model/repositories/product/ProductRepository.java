package com.generation.stockybackend.model.repositories.product;

import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.model.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>
{
	@Query("SELECT p FROM Product p JOIN p.options o WHERE o.time >= :start AND o.time <= :end" )
	List<Product> findOperationTimeBetween(LocalDateTime start, LocalDateTime end);

//	@Query("SELECT p FROM Product p JOIN p.options o WHERE o.time =:when" )
//	List<Product> findByHourAndDate(LocalDateTime when);

	@Query("SELECT p FROM Product p JOIN p.options o WHERE o.user.id = :id")
	List<Product> findProductByUserId(UUID id);

	List<Product> findByWeight(double weigth);

	List<Product> findProductByCategory(String category);

	List<Product> findProductByName(String name);

	List<Product> findProductByInStock(Boolean inStock);



}
