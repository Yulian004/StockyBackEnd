package com.generation.stockybackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserFilter filtro;

    @Bean
    public SecurityFilterChain sicurezza(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth->
                        auth
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/userInformation").hasRole("STANDARD")
                                .requestMatchers("/api/adminController/**").hasRole("ADMIN")
                                .requestMatchers("/api/auth/register").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST).hasRole("SUPERVISOR")
                                .requestMatchers(HttpMethod.PUT).authenticated()
                                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE).hasRole("SUPERVISOR")
                                .anyRequest().permitAll()
                ).addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
    @Bean
    public PasswordEncoder getCypher() {
        return new BCryptPasswordEncoder();
    }
}
