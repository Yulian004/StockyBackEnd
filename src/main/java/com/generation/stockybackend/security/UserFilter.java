package com.generation.stockybackend.security;

import com.generation.stockybackend.model.entities.User;
import com.generation.stockybackend.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserFilter extends OncePerRequestFilter
{
    @Autowired
    private UserService serv;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        Cookie[] cookies = request.getCookies();
        String token =extractToken(cookies);

        if(token != null)
        {

            User u = serv.findUserByToken(token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(Cookie[] cookies)
    {
        if(cookies == null)
            return null;
        for(Cookie cookie : cookies)
            if(cookie.getName().equals("token"))
                return cookie.getValue();

        return null;
    }
}
