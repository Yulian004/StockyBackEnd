package com.generation.stockybackend.controllers;

import com.generation.stockybackend.model.dtos.product.ProductOutputDto;
import com.generation.stockybackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController
{
	@Autowired
	private ProductService productServ;

	/**
	 * Lista di tutti i prodotti
	 */

	@GetMapping("/products")
	public List<ProductOutputDto> findAllAsDtos()
	{
		return productServ.findAllAsDtos();
	}

	//Aggiunta prodotti
	@PostMapping("/add")
	public void addQuantity(@RequestParam UUID id, @RequestParam int amount)
	{
		productServ.addQuantity(id, amount);
	}

	//Sottrazione prodotti/vendita
	@PostMapping("/sub")
	public void subQuantity(@RequestParam UUID id, @RequestParam int amount)
	{
		productServ.subQuantity(id, amount);
	}

	//Modifica quantità
	@PostMapping("/adjust")
	public void adjustQuantity(@RequestParam UUID id, @RequestParam int amount)
	{
		productServ.adjustQuantity(id, amount);
	}


}
