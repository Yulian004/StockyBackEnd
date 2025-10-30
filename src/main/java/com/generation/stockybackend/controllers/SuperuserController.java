package com.generation.stockybackend.controllers;

import com.generation.stockybackend.model.dtos.options.OptionsOutputDto;
import com.generation.stockybackend.model.dtos.product.ProductInputDto;
import com.generation.stockybackend.model.dtos.product.ProductOutputDto;
import com.generation.stockybackend.model.dtos.section.TransferRequestDto;
import com.generation.stockybackend.model.dtos.section.TransferResultDto;
import com.generation.stockybackend.services.OptionsService;
import com.generation.stockybackend.services.SectionService;
import com.generation.stockybackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/superuser")
public class SuperuserController
{
	@Autowired
	SectionService sectionServ;

	@Autowired
	private ProductService productServ;
	@Autowired
	private OptionsService optionsServ;

	@GetMapping("/productlist")
	public List<ProductOutputDto> findAllAsDtos()
	{
		return productServ.findAllAsDtos();
	}

//	@PostMapping("/addproduct")
//	public void addQuantity(@PathVariable UUID id, @PathVariable int quantita)
//	{
//		productServ.addQuantity(id, quantita);
//	}
//
//	@PostMapping("/subproduct")
//	public void subQuantity(@PathVariable UUID id, @PathVariable int quantita)
//	{
//		productServ.subQuantity(id, quantita);
//	}
//
//	@PostMapping("/adjustproduct")
//	public void adjustQuantity(@PathVariable UUID id, @PathVariable int quantita)
//	{
//		productServ.adjustQuantity(id, quantita);
//	}

	@DeleteMapping("/delete")
	public void delete(@RequestParam UUID id){productServ.delete(id);}

	@PutMapping("/update")
	public void update(@RequestParam UUID id, @RequestParam ProductInputDto dto) {productServ.update(id, dto);}

	@PostMapping("/create")
	public void create(@RequestBody ProductInputDto dto) {productServ.create(dto);}

	//lista di tutte le operazioni
	@GetMapping("/alloperations")
	public List<OptionsOutputDto> optionsFindAllAsDtos()
	{
		return optionsServ.findAllAsDtos();
	}

	//filtro operazioni in un lasso di tempo
	@GetMapping("/operationtime")
	public List<OptionsOutputDto> OperationsInTime(@RequestParam LocalDate day, @RequestParam LocalTime from, @RequestParam LocalTime to)
	{
		return optionsServ.OperationsInTime(day, from, to);
	}

	//filtro per operazioni per utente
	@GetMapping("/useroperations")
	public List<OptionsOutputDto> getOperationUser(@RequestParam UUID id)
	{
		return optionsServ.getOperationUser(id);
	}

	//trasferimento prodotti da sezione a sezione
	@PostMapping("/transfer")
	public ResponseEntity<TransferResultDto> transferProduct(@RequestBody TransferRequestDto dto)
	{
		if(!sectionServ.transferProduct(dto))
			return ResponseEntity.badRequest().build();

		TransferResultDto result = sectionServ.getTransferResult(dto);

		return ResponseEntity.ok(result);
	}


}
