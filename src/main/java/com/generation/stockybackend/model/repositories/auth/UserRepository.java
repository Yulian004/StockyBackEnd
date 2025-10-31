package com.generation.stockybackend.model.repositories.auth;

import com.generation.stockybackend.model.dtos.auth.UserOutputDto;
import com.generation.stockybackend.model.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    boolean existsByEmail(String email);
    List<User> findByRegistrationDateGreaterThanEqual(LocalDate start);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail2(String email);
}
