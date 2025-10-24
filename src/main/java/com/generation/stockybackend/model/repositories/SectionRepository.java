package com.generation.stockybackend.model.repositories;

import com.generation.stockybackend.model.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SectionRepository extends JpaRepository<Section, UUID> {
}
