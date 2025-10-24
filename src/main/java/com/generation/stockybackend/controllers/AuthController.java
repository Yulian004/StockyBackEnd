package com.generation.stockybackend.controllers;

import com.generation.stockybackend.model.dtos.auth.LoginDto;
import com.generation.stockybackend.model.dtos.auth.RegisterDto;
import com.generation.stockybackend.model.dtos.auth.UserOutputDto;
import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController
{
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public void register(@RequestBody RegisterDto dto)
    {
        userService.register(dto);

    }

    @PostMapping("login")
    public void login(@RequestBody LoginDto dto, HttpServletResponse response)
    {
        String tokenUtente = userService.login(dto);

        Cookie cookie = new Cookie("token", tokenUtente);
        cookie.setMaxAge(3600);
        cookie.setPath("/api");
        response.addCookie(cookie);
    }


    @GetMapping("/userinformation")
    public UserOutputDto getUserInfo(@AuthenticationPrincipal User user)
    {
        return userService.convertToUserDto(user);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String gestisciTutto(Exception e)
    {
        return "Operazione fallita, ulteriori dettagli "+e.getMessage();
    }
}