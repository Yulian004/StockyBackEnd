package com.generation.stockybackend.controllers;


import com.generation.stockybackend.model.dtos.auth.RegisterDto;
import com.generation.stockybackend.model.dtos.auth.UserOutputDto;
import com.generation.stockybackend.model.dtos.product.ProductOutputDto;
import com.generation.stockybackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	@PostMapping("/createuser")
	public String register(@RequestParam RegisterDto dto)
	{
		return userServ.register(dto);
	}

	@PostMapping("/userrole")
	public void update(@RequestParam UUID id,@RequestParam RegisterDto dto)
	{
		return userServ.update(id, dto);
	}

}
