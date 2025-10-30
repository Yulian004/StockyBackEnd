package com.generation.stockybackend.model.repositories;

import com.generation.stockybackend.model.entities.IntercomMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntercomMessageRepository extends JpaRepository<IntercomMessage, Long>
{

}
