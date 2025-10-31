package com.generation.stockybackend.model.dtos.section;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransferResultDto {
	private UUID productId;
	private String productName;
	private int startSection;
	private int endSection;
	private double availableWeightInEndSection;
	private double availableVolumeInEndSection;
}