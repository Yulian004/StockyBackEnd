package com.generation.stockybackend.services.products;

import com.generation.stockybackend.model.dtos.product.ProductInputDto;
import com.generation.stockybackend.model.dtos.product.ProductOutputDto;
import com.generation.stockybackend.model.dtos.section.SectionInputDto;
import com.generation.stockybackend.model.dtos.section.SectionOutputDto;
import com.generation.stockybackend.model.entities.Options;
import com.generation.stockybackend.model.entities.Section;
import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.model.entities.products.Clothes;
import com.generation.stockybackend.model.entities.products.Electronics;
import com.generation.stockybackend.model.entities.products.Food;
import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.enums.OptionType;
import com.generation.stockybackend.model.enums.StockStatus;
import com.generation.stockybackend.model.repositories.SectionRepository;
import com.generation.stockybackend.model.repositories.product.ClothesRepository;
import com.generation.stockybackend.model.repositories.product.ElectronicsRepository;
import com.generation.stockybackend.model.repositories.product.FoodRepository;
import com.generation.stockybackend.model.repositories.product.ProductRepository;
import com.generation.stockybackend.services.OptionsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService
{
	@Autowired
	private ProductRepository repo;
	@Autowired
	private SectionRepository sRepo;
	@Autowired
	private FoodRepository fRepo;
	@Autowired
	private ClothesRepository cRepo;
	@Autowired
	private ElectronicsRepository eRepo;
	@Autowired
	private OptionsService oRepo;


	public List<ProductOutputDto> findAllAsDtos()
	{
		return repo.findAll().stream().map(this::convertToDto).toList();
	}

	public ProductOutputDto convertToDto(Product p)
	{

		ProductOutputDto res = new ProductOutputDto();
		switch (p.getClass().getSimpleName().toLowerCase())
		{
			case "clothes" ->
			{
				Clothes c = (Clothes)p;
				res.setBrand(c.getBrand());
				res.setMaterial(c.getMaterial());
				res.setColor(c.getColor());
				res.setSize(c.getSize());

			}
			case "food" ->
			{
				Food f = (Food)p;
				res.setExpire_date(f.getExpire_date());
				res.setBio(f.isBio());
				res.setCold(f.getCold());

			}
			case "electronics" ->
			{
				Electronics e = (Electronics)p;
				res.setWarranty_months(e.getWarranty_months());
				res.setEnergy_class(e.getEnergy_class());
			}
		}
		res.setId(p.getId());
		res.setName(p.getName());
		res.setCategory(p.getCategory());
		res.setQuantity(p.getQuantity());
		res.setInitialQuantity(p.getInitialQuantity());
		res.setUnit_price(p.getUnit_price());
		res.setWeight(p.getWeight());
		res.setVolume(p.getVolume());
		res.setInStock(p.getQuantity() > 0);
		res.setStatus(p.getStatus());

		if (p.getSection() != null)
		{
			res.setSectionId(p.getSection().getId());
			res.setNumber(p.getSection().getNumber());
			res.setRoom(p.getSection().getRoom());
		}

		return res;
	}

	public Product convertToEntity(ProductInputDto dto)
	{
		Product p;
		switch (dto.getType().toLowerCase())
		{
			case "clothes" ->
			{
				Clothes c = new Clothes();
				c.setBrand(dto.getBrand());
				c.setMaterial(dto.getMaterial());
				c.setColor(dto.getColor());
				c.setSize(dto.getSize());
				p = c;
			}
			case "food" ->
			{
				Food f = new Food();
				f.setExpire_date(dto.getExpire_date());
				f.setBio(dto.isBio());
				f.setCold(dto.getCold());
				p = f;
			}
			case "electronics" ->
			{
				Electronics e = new Electronics();
				e.setWarranty_months(dto.getWarranty_months());
				e.setEnergy_class(dto.getEnergy_class());
				p = e;
			}
			default -> p = new Product();
		}
		p.setName(dto.getName());
		p.setCategory(dto.getCategory());
		p.setQuantity(dto.getQuantity());
		p.setInitialQuantity(dto.getQuantity());
		p.setUnit_price(dto.getUnit_price());
		p.setWeight(dto.getWeight());
		p.setVolume(dto.getVolume());

		if (dto.getSectionId() != null)
		{
			Section section = sRepo.findById(dto.getSectionId()).orElseThrow(() -> new EntityNotFoundException("Section not found"));
			p.setSection(section);
		}
		return p;
	}

	public void delete(UUID id)
	{
		repo.deleteById(id);
	}

	public void addQuantity(UUID id, int quantity)
	{
		if (quantity <= 0) throw new IllegalArgumentException("Amount must be positive");

		Product product = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));


		Options op = new Options();
		op.setProduct(product);
		op.setType(OptionType.ADD);
		op.setQuantityChange(quantity);

		oRepo.applyOption(op);

	}

	public void subQuantity(UUID id, int quantity)
	{
		if (quantity <= 0) throw new IllegalArgumentException("Amount must be positive");

		Product product = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));


		Options op = new Options();
		op.setProduct(product);
		op.setType(OptionType.SUB);
		op.setQuantityChange(quantity);

		oRepo.applyOption(op);

	}

	public void adjustQuantity(UUID id, int quantity)
	{
		if (quantity <= 0) throw new IllegalArgumentException("Amount must be positive");

		Product product = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));


		Options op = new Options();
		op.setProduct(product);
		op.setType(OptionType.ADJUSTMENT);
		op.setQuantityChange(quantity);

		oRepo.applyOption(op);

	}

	public void update(UUID id, ProductInputDto dto)
	{
		Product res = repo.findById(id)
				.orElseThrow( () -> new EntityNotFoundException("Section with %s not found".formatted(id)));
		res.setName(dto.getName());
		res.setCategory(dto.getCategory());
		res.setQuantity(dto.getQuantity());
		res.setUnit_price(dto.getUnit_price());
		res.setWeight(dto.getWeight());
		res.setVolume(dto.getVolume());
		Section section = sRepo.findById(dto.getSectionId())
				.orElseThrow(() -> new EntityNotFoundException("Section %s not found".formatted(dto.getSectionId())));
		res.setSection(section);

		Product product = repo.save(res);

	}

	public List<ProductOutputDto> getProductFromOperationTime(LocalDate day, LocalTime from, LocalTime to)
	{
		LocalDateTime start = day.atTime(from);
		LocalDateTime end = day.atTime(to);

		if (!end.isAfter(start)) end = end.plusDays(1);

		return  repo.findOperationTimeBetween(start, end).stream()
				.map(this::convertToDto).toList();
	}

	public List<ProductOutputDto> productWithUser(UUID id)
	{
		return repo.findProductByUserId(id).stream().map(this::convertToDto).toList();
	}


//	public List<ProductOutputDto> productFlexibleHours(LocalDate day, LocalTime hour)
//	{
//		LocalDateTime start = day.atTime(hour.getHour(), 0 , 0);
//		LocalDateTime end = start.plusHours(1);
//
//		return  repo.findOperationTimeBetween(start, end).stream()
//				.map(this::convertToDto).toList();
//	}

	public List<Product> productByCategory(String category)
	{
		List<Product> prodotti = repo.findProductByCategory(category);
		if(prodotti.isEmpty())
		{
			throw new RuntimeException("No product found");
		}
		return prodotti;
	}
	public List<Product> productByName(String name)
	{
		List<Product> prodotti = repo.findProductByName(name);
		if(prodotti.isEmpty())
		{
			throw new RuntimeException("No product found");
		}
		return prodotti;
	}
	public List<Food> productByExpirationDate(LocalDate date)
	{

		List<Food> food = fRepo.findFoodByExpire_date(date);
		if(food.isEmpty())
		{
			throw new RuntimeException("No product found");
		}

		return food;
	}
	public List<Product> isInStock(boolean stock)
	{
		List<Product> prodotti = repo.findProductByInStock(stock);
		if(prodotti.isEmpty())
		{
			throw new RuntimeException("No product found");
		}
		return prodotti;
	}
	public List<Clothes> productBySize(String size)
	{
		List<Clothes> clothes = cRepo.findClothesBySize(size);
		if(clothes.isEmpty())
		{
			throw new RuntimeException("No product found");
		}
		return clothes;
	}
	public List<Clothes> productByBrand(String brand)
	{
		List<Clothes> clothes = cRepo.findClothesByBrand(brand);
		if(clothes.isEmpty())
		{
			throw new RuntimeException("No product found");
		}
		return clothes;
	}

	public void create(ProductInputDto dto)
	{
		Product p = convertToEntity(dto);
		repo.save(p);

	}


	//Prodotti: Categoria, nome, stock, data_scadenza, sezione, size, brand, FiltroProdottiPerUtente, FiltroProdottiPerOrario
	//Operazioni: Tempo, Tipo, FiltroOperazioniPerUtente
	//Sezioni:



}
