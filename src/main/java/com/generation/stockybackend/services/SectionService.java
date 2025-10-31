package com.generation.stockybackend.services;

import com.generation.stockybackend.model.dtos.section.SectionInputDto;
import com.generation.stockybackend.model.dtos.section.SectionOutputDto;
import com.generation.stockybackend.model.dtos.section.TransferRequestDto;
import com.generation.stockybackend.model.dtos.section.TransferResultDto;
import com.generation.stockybackend.model.entities.Section;
import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.repositories.SectionRepository;
import com.generation.stockybackend.model.repositories.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SectionService
{
	@Autowired
	private SectionRepository repo;

	@Autowired
	private ProductRepository pRepo;

	public List<SectionOutputDto> findAllAsDtos()
	{
		return repo.findAll().stream().map(this::convertToDto).toList();
	}

	public void saveWithDtoInput(SectionInputDto dto)
	{
		Section e = convertToEntity(dto);
		repo.save(e);
	}

	private SectionOutputDto convertToDto(Section e)
	{
		SectionOutputDto res = new SectionOutputDto();
		res.setId(e.getId());
		res.setCategory(e.getCategory());
		res.setNumber(e.getNumber());
		res.setRoom(e.getRoom());
		res.setMaxWeight(e.getMaxWeight());
		res.setMaxVolume(e.getMaxVolume());

		return res;
	}

	private Section convertToEntity(SectionInputDto dto)
	{
		Section res = new Section();
		res.setCategory(dto.getCategory());
		res.setNumber(dto.getNumber());
		res.setRoom(dto.getRoom());
		res.setMaxWeight(dto.getMaxWeight());
		res.setMaxVolume(dto.getMaxVolume());

		return res;
	}

	public boolean canEnter(Section section, String category, double weight, double volume)
	{
		if (section == null || category == null) return false;

		boolean categoryMatch = section.getCategory().equalsIgnoreCase(category);
		boolean weightMatch = weight <= section.availableWeight();
		boolean volumeMatch = volume <= section.availableVolume();

		return categoryMatch && weightMatch && volumeMatch;
	}

	public  boolean transferProduct(TransferRequestDto dto)
	{
		Optional<Section> startOpt = repo.findById(dto.getStartSectionId());
		Optional<Section> endOpt = repo.findById(dto.getEndSectionId());
		Optional<Product> productOpt = pRepo.findById(dto.getProductId());

		if (startOpt.isEmpty()|| endOpt.isEmpty()||productOpt.isEmpty())
			return false;

		Section start = startOpt.get();
		Section end = endOpt.get();
		Product product = productOpt.get();

		if (!start.getProducts().contains(product))
			return false;

		if (!canEnter(end, product.getCategory(), product.getWeight(), product.getVolume()))
			return false;

		start.getProducts().remove(product);
		end.addProduct(product);

		repo.save(start);
		repo.save(end);
		pRepo.save(product);

		return true;
	}

	public TransferResultDto getTransferResult(TransferRequestDto dto)
	{
		Product product = pRepo.findById(dto.getProductId()).orElseThrow();
		Section start = repo.findById(dto.getStartSectionId()).orElseThrow();
		Section end = repo.findById(dto.getEndSectionId()).orElseThrow();

		return new TransferResultDto(
				product.getId(),
				product.getName(),
				start.getNumber(),
				end.getNumber(),
				end.availableWeight(),
				end.availableVolume()
		);
	}

	public void delete(UUID id)
	{
		repo.deleteById(id);
	}

	public SectionOutputDto update(UUID id, SectionInputDto dto)
	{
		Section res = repo.findById(id)
				.orElseThrow( () -> new EntityNotFoundException("Section with %s not found".formatted(id)));
		res.setCategory(dto.getCategory());
		res.setRoom(dto.getRoom());
		res.setNumber(dto.getNumber());
		res.setMaxWeight(dto.getMaxWeight());
		res.setMaxVolume(dto.getMaxVolume());

		Section section = repo.save(res);
		return convertToDto(section);

	}
	public List<SectionOutputDto> SectionsMaxWeight(double maxWeight)
	{
		List<Section> sezioni = repo.findByMaxWeightGreaterThan(maxWeight);
		if (sezioni.isEmpty())
		{
			throw new RuntimeException("Sections not found");
		}
		return sezioni.stream().map(this::convertToDto).toList();
	}

	public List<SectionOutputDto> SectionsMaxVolume(double maxVolume)
	{
		List<Section> sezioni = repo.findByMaxVolumeGreaterThan(maxVolume);
		if (sezioni.isEmpty())
		{
			throw new RuntimeException("Sections not found");
		}
		return sezioni.stream().map(this::convertToDto).toList();
	}

	public List<SectionOutputDto> SectionsCategory(String category)
	{
		List<Section> sezioni = repo.findByCategory(category);
		if (sezioni.isEmpty())
		{
			throw new RuntimeException("Sections not found");
		}
		return sezioni.stream().map(this::convertToDto).toList();
	}

	public List<SectionOutputDto> SectionsRoom(String room)
	{
		List<Section> sezioni = repo.findByRoom(room);
		if (sezioni.isEmpty())
		{
			throw new RuntimeException("Sections not found");
		}
		return sezioni.stream().map(this::convertToDto).toList();
	}

	public List<SectionOutputDto> SectionsNumber(int number)
	{
		List<Section> sezioni = repo.findByNumber(number);
		if (sezioni.isEmpty())
		{
			throw new RuntimeException("Sections not found");
		}
		return sezioni.stream().map(this::convertToDto).toList();
	}




}
