package com.generation.stockybackend.controllers;

import com.generation.stockybackend.model.dtos.auth.UserOutputDto;
import com.generation.stockybackend.model.dtos.product.ProductOutputDto;
import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.services.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController
{
	@Autowired
	private ProductService productServ;
	@GetMapping("/productlist")
	public List<ProductOutputDto> findAllAsDtos()
	{
		return productServ.findAllAsDtos();
	}
	@PostMapping("/addproduct")
	public void addQuantity(@RequestParam UUID id, @RequestParam int quantita)
	{
		productServ.addQuantity(id, quantita);
	}

	@PostMapping("/subproduct")
	public void subQuantity(@RequestParam UUID id, @RequestParam int quantita)
	{
		productServ.subQuantity(id, quantita);
	}

	@PostMapping("/adjustproduct")
	public void adjustQuantity(@RequestParam UUID id, @RequestParam int quantita)
	{
		productServ.adjustQuantity(id, quantita);
	}


}
