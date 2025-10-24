package com.generation.stockybackend.services;

import com.generation.stockybackend.exceptions.InvalidCredentials;
import com.generation.stockybackend.model.dtos.auth.LoginDto;
import com.generation.stockybackend.model.dtos.auth.RegisterDto;
import com.generation.stockybackend.model.dtos.auth.UserOutputDto;
import com.generation.stockybackend.model.entities.auth.Role;
import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.model.repositories.auth.RoleRepository;
import com.generation.stockybackend.model.repositories.auth.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Log4j2
@Service
public class UserService
{
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder encoder;



    public String register(RegisterDto dto)
    {
        if (!dto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            throw new InvalidCredentials("Invalid Password");

        User u = new User();
        u.setName(dto.getName());
        u.setSurname(dto.getSurname());
        u.setEmail(dto.getEmail());
        String hash = encoder.encode(dto.getPassword());
        u.setPassword(hash);

        if(dto.getRole()!=null)
            u.setRoles(List.of(roleRepo.getUserRole(),roleRepo.findByRoleName(dto.getRole().toUpperCase()).get()));
        else
            u.setRoles(List.of(roleRepo.getUserRole()));
        u.setToken(UUID.randomUUID().toString());

        repo.save(u);
        log.info("Utente registrato con mail "+ u.getEmail());
        return u.getToken();

    }

    public String login(LoginDto dto)
    {
        Optional<User> op = repo.findByEmail(dto.getEmail());
        if (op.isEmpty())
            throw new InvalidCredentials("Invalid Email");
        if (!encoder.matches(dto.getPassword(), op.get().getPassword()))
            throw new InvalidCredentials("Invalid Password");

        return op.get().getToken();

    }

    public User findUserByToken(String token)
    {
        Optional<User> op = repo.findByToken(token);

        if (op.isEmpty())
            throw new InvalidCredentials("Invalid Token");

        return op.get();
    }

    public UserOutputDto readUserDto(String token)
    {
        User u = findUserByToken(token);
        UserOutputDto dto = convertToUserDto(u);
        return dto;
    }

    public UserOutputDto convertToUserDto(User u)
    {
        UserOutputDto dto = new UserOutputDto();
        dto.setName(u.getName());
        dto.setSurname(u.getSurname());
        dto.setEmail(u.getEmail());
        dto.setRoles(u.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));

        return dto;
    }
}
