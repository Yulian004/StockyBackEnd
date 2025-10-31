package com.generation.stockybackend.model.dtos.section;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransferRequestDto {
	private UUID startSectionId;
	private UUID endSectionId;
	private UUID productId;
}
