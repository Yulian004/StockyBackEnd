package com.generation.stockybackend.controllers;

import com.generation.stockybackend.model.dtos.section.TransferRequestDto;
import com.generation.stockybackend.model.dtos.section.TransferResultDto;
import com.generation.stockybackend.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sections")
public class SectionController
{

	@Autowired
	SectionService serv;

	@PostMapping("/transfer")
	public ResponseEntity<TransferResultDto> transferProduct(@RequestBody TransferRequestDto dto)
	{
		if(!serv.transferProduct(dto))
			return ResponseEntity.badRequest().build();

		TransferResultDto result = serv.getTransferResult(dto);

		return ResponseEntity.ok(result);
	}

}
