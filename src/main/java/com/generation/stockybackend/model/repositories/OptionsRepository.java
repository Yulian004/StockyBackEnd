package com.generation.stockybackend.model.repositories;

import com.generation.stockybackend.model.entities.Options;
import com.generation.stockybackend.model.entities.Section;
import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.enums.OptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OptionsRepository extends JpaRepository<Options, UUID>
{
	@Query("SELECT o FROM Options o WHERE o.time >= :start AND o.time <= :end" )
	List<Options> findOperationTimeBetween(LocalDateTime start, LocalDateTime end);

	List<Options> findByType(OptionType type);


	List<Options> findOperationByUserId(UUID userId);
}
