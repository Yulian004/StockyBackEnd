package com.generation.stockybackend.model.repositories.auth;

import com.generation.stockybackend.model.dtos.auth.UserOutputDto;
import com.generation.stockybackend.model.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    boolean existsByEmail(String email);
    List<User> findByRegistrationDateGreaterThanEqual(LocalDate start);

    User findByEmail2(String email);
}
