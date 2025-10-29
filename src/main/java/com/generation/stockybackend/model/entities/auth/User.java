package com.generation.stockybackend.model.entities.auth;

import com.generation.stockybackend.model.entities.BaseEntity;
import com.generation.stockybackend.model.entities.Options;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    private String name;
    private String surname;
    private String password;
    @Column(unique = true)
    private String email;
    private String token;
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Options> options = new HashSet<>();

    public void addOption(Options option)
    {
        options.add(option);
        option.setUser(this);
    }
}
