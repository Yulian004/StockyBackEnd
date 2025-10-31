package com.generation.stockybackend.controllers;


import com.generation.stockybackend.model.dtos.auth.RegisterDto;
import com.generation.stockybackend.model.dtos.auth.UserOutputDto;
import com.generation.stockybackend.model.dtos.product.ProductOutputDto;
import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	private UserService userServ;

	@GetMapping("/userlist")
	public List<UserOutputDto> findAllAsDtos()
	{
		return userServ.findAllAsDtos();
	}
	@PostMapping("/create")
	public String register(@RequestBody RegisterDto dto)
	{
		return userServ.register(dto);
	}

	@PutMapping("/modify")
	public void update(@RequestParam UUID id, @RequestBody RegisterDto dto)
	{
		userServ.update(id, dto);
	}

//    @GetMapping("/userfromdate")
//    public List<UserOutputDto> userRegisteredFromDate (@RequestParam LocalDate start)
//    {
//        return userServ.userRegisteredFromDate (start);
//    }

}
