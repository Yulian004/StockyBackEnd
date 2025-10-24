package com.generation.stockybackend.model.repositories;

import com.generation.stockybackend.model.entities.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OptionsRepository extends JpaRepository<Options, UUID> {
}
