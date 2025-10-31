package com.generation.stockybackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                                .requestMatchers("/api/login").permitAll()
                                .requestMatchers("/api/userInformation").hasRole("STANDARD")
                                .requestMatchers("/api/adminController/**").hasRole("ADMIN")
                                .requestMatchers("/api/SupervisorController/**").hasRole("SUPERVISOR")
                                .requestMatchers("/api/register").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN","SUPERVISOR")
                                .requestMatchers(HttpMethod.PUT,"/api/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ADMIN","SUPERVISOR")
                                .anyRequest().permitAll()
                ).addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
    @Bean
    public PasswordEncoder getCypher() {
        return new BCryptPasswordEncoder();
    }



//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails admin = User.builder()
//                .username("admin@email.com")
//                .password(getCypher().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(admin);
//    }
}
