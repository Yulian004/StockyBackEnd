package com.generation.stockybackend.model.repositories;

import com.generation.stockybackend.model.entities.Section;
import com.generation.stockybackend.model.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SectionRepository extends JpaRepository<Section, UUID>
{


	@Query("SELECT s FROM Section s Where s.maxWeight >= :maxWeight")
	List<Section> findByMaxWeightGreaterThan(double maxWeight);

	@Query("SELECT s FROM Section s Where s.maxVolume >= :maxVolume")
	List<Section> findByMaxVolumeGreaterThan(double maxVolume);

	@Query("select s from Section s where s.category = :category")
	List<Section> findByCategory (String category);

	@Query("select s from Section s where s.room = :room")
	List<Section> findByRoom (String room);

	@Query("select s from Section s where s.number = :number")
	List<Section> findByNumber (int number);
}
