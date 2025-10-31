package com.generation.stockybackend;

import com.generation.stockybackend.model.entities.Options;
import com.generation.stockybackend.model.entities.Section;
import com.generation.stockybackend.model.entities.products.Clothes;
import com.generation.stockybackend.model.entities.products.Electronics;
import com.generation.stockybackend.model.entities.products.Food;
import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.enums.Cold_Food;
import com.generation.stockybackend.model.enums.OptionType;
import com.generation.stockybackend.model.enums.Size;
import com.generation.stockybackend.model.enums.StockStatus;
import com.generation.stockybackend.model.repositories.SectionRepository;
import com.generation.stockybackend.model.repositories.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class test {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SectionRepository sectionRepository;

	private final Random rnd = new Random(123);

	@Test
	@DisplayName("Persist tutte le entità usando solo le repository: Sections, 10 Clothes, 10 Electronics, 20 Food, 20 Generic con Options realistiche")
	void insertAllWithRepositoriesOnly() {
		// 1) Persistiamo prima le Section con la SectionRepository
		List<Section> sections = buildSections();
		sections = sectionRepository.saveAll(sections);

		// 2) Costruiamo i prodotti e li leghiamo alle Section già persistite
		List<Clothes> clothes = buildClothes(10, sections);
		List<Electronics> electronics = buildElectronics(10, sections);
		List<Food> foods = buildFood(20, sections);
		List<Product> generic = buildGenericProducts(20, sections);

		// 3) Aggiungiamo Options realistiche e aggiorniamo quantità/stato
		attachOptionsForClothes(clothes);
		attachOptionsForElectronics(electronics);
		attachOptionsForFood(foods);
		attachOptionsForGeneric(generic);

		// 4) Salviamo tutto via ProductRepository (cascade su Options)
		List<Product> all = new ArrayList<>();
		all.addAll(clothes);
		all.addAll(electronics);
		all.addAll(foods);
		all.addAll(generic);

		productRepository.saveAll(all);

		// 5) Verifiche tramite repository
		long count = productRepository.count();
		assertThat(count).isEqualTo(60);

		List<Product> reloaded = productRepository.findAll();
		assertThat(reloaded).hasSize(60);

		for (Product p : reloaded) {
			assertThat(p.getSection()).isNotNull();
			assertThat(p.getOptions()).isNotNull();
			assertThat(p.getOptions().size()).isGreaterThanOrEqualTo(2);
			for (Options o : p.getOptions()) {
				assertThat(o.getProduct()).isNotNull();
				assertThat(o.getType()).isIn(OptionType.ADD, OptionType.SUB, OptionType.ADJUSTMENT);
			}
			assertThat(p.getInitialQuantity()).isGreaterThan(0);
			assertThat(p.getQuantity()).isGreaterThanOrEqualTo(0);
			assertThat(p.getStatus()).isIn(StockStatus.OK, StockStatus.LOW, StockStatus.CRITICAL, StockStatus.OUT_OF_STOCK);
			assertThat(p.getInStock()).isEqualTo(p.getQuantity() > 0);
		}
	}

	// ---------------------- Builders di sezioni ----------------------

	private List<Section> buildSections() {
		List<Section> sections = new ArrayList<>();
		sections.add(makeSection("FOOD", "A", 1, 2500, 3500));
		sections.add(makeSection("FOOD", "A", 2, 2500, 3500));
		sections.add(makeSection("ELECTRONICS", "B", 3, 1800, 2200));
		sections.add(makeSection("CLOTHES", "C", 4, 1200, 1800));
		sections.add(makeSection("GENERIC", "D", 5, 2000, 2600));
		sections.add(makeSection("GENERIC", "D", 6, 2200, 3000));
		return sections;
	}

	private Section makeSection(String category, String room, int number, double maxWeight, double maxVolume) {
		Section s = new Section();
		s.setCategory(category);
		s.setRoom(room);
		s.setNumber(number);
		s.setMaxWeight(maxWeight);
		s.setMaxVolume(maxVolume);
		return s;
	}

	// ---------------------- Builders di prodotti ----------------------

	private List<Clothes> buildClothes(int n, List<Section> sections) {
		List<Clothes> list = new ArrayList<>();
		Size[] sizes = Size.values();
		String[] colors = {"Black", "Blue", "Navy", "Beige", "White", "Gray"};
		String[] materials = {"Cotton", "Wool", "Denim", "Polyester"};

		for (int i = 1; i <= n; i++) {
			Clothes c = new Clothes();
			String name = "T-Shirt " + i;
			Section sec = pickSection(sections, "CLOTHES", "GENERIC");
			int initial = 40 + rnd.nextInt(60);
			int qty = (int) Math.round(initial * (0.4 + rnd.nextDouble() * 0.7));
			fillCommonFields(c, name, "CLOTHES", initial, qty, 15 + rnd.nextInt(40),
					0.25 + rnd.nextDouble() * 0.4, 0.004 + rnd.nextDouble() * 0.006, sec);

			c.setBrand("Brand-" + ((i % 5) + 1));
			c.setMaterial(materials[rnd.nextInt(materials.length)]);
			c.setColor(colors[rnd.nextInt(colors.length)]);
			c.setSize(sizes[rnd.nextInt(sizes.length)]);

			list.add(c);
		}
		return list;
	}

	private List<Electronics> buildElectronics(int n, List<Section> sections) {
		List<Electronics> list = new ArrayList<>();
		String[] classes = {"A+", "A++", "A", "B"};

		for (int i = 1; i <= n; i++) {
			Electronics e = new Electronics();
			String name = "Device-" + i;
			Section sec = pickSection(sections, "ELECTRONICS", "GENERIC");
			int initial = 10 + rnd.nextInt(25);
			int qty = Math.max(0, initial - rnd.nextInt(10));
			fillCommonFields(e, name, "ELECTRONICS", initial, qty, 150 + rnd.nextInt(450),
					1.2 + rnd.nextDouble() * 3.5, 0.006 + rnd.nextDouble() * 0.02, sec);

			e.setWarranty_months(rnd.nextBoolean() ? 24 : 12);
			e.setEnergy_class(classes[rnd.nextInt(classes.length)]);

			list.add(e);
		}
		return list;
	}

	private List<Food> buildFood(int n, List<Section> sections) {
		List<Food> list = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			Food f = new Food();
			String name = (i % 2 == 0 ? "Yogurt" : "Pasta") + " " + i;
			Section sec = pickSection(sections, "FOOD", "GENERIC");
			int initial = 60 + rnd.nextInt(90);
			int qty = Math.max(0, initial - rnd.nextInt(40));
			fillCommonFields(f, name, "FOOD", initial, qty, 2 + rnd.nextInt(8),
					0.3 + rnd.nextDouble() * 0.7, 0.002 + rnd.nextDouble() * 0.006, sec);

			f.setCold(rnd.nextBoolean() ? Cold_Food.REFRIGERATED : Cold_Food.FROZEN);
			f.setBio(rnd.nextInt(100) < 25);
			f.setExpirationDate(LocalDate.now().plusDays(7 + rnd.nextInt(114)));

			list.add(f);
		}
		return list;
	}

	private List<Product> buildGenericProducts(int n, List<Section> sections) {
		List<Product> list = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			Product p = new Product();
			String name = "Item-" + i;
			Section sec = pickSection(sections, "GENERIC", null);
			int initial = 30 + rnd.nextInt(80);
			int qty = Math.max(0, initial - rnd.nextInt(20));
			fillCommonFields(p, name, "GENERIC", initial, qty, 5 + rnd.nextInt(40),
					0.6 + rnd.nextDouble() * 1.2, 0.004 + rnd.nextDouble() * 0.01, sec);
			list.add(p);
		}
		return list;
	}

	private void fillCommonFields(Product p, String name, String category,
								  int initialQty, int qty, int unitPrice,
								  double weight, double volume, Section section) {
		p.setName(name);
		p.setCategory(category);
		p.setInitialQuantity(initialQty);
		p.setQuantity(qty);
		p.setUnit_price(unitPrice);
		p.setWeight(weight);
		p.setVolume(volume);
		p.setInStock(qty > 0);
		p.setSection(section);
		if (section != null) {
			section.addProduct(p);
		}
		p.checkLevel();
	}

	private Section pickSection(List<Section> sections, String preferredCategory, String fallbackCategory) {
		List<Section> candidates = new ArrayList<>();
		for (Section s : sections) {
			if (preferredCategory != null && preferredCategory.equalsIgnoreCase(s.getCategory())) {
				candidates.add(s);
			}
		}
		if (candidates.isEmpty() && fallbackCategory != null) {
			for (Section s : sections) {
				if (fallbackCategory.equalsIgnoreCase(s.getCategory())) {
					candidates.add(s);
				}
			}
		}
		return candidates.isEmpty() ? sections.get(0) : candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
	}

	// ---------------------- Options realistiche per categoria ----------------------

	private void attachOptionsForClothes(List<Clothes> items) {
		for (Product p : items) {
			ensureOptionsList(p);
			addOption(p, OptionType.ADD, 5 + rnd.nextInt(10));
			addOption(p, OptionType.SUB, 1 + rnd.nextInt(3));
			addOption(p, OptionType.ADJUSTMENT, rnd.nextBoolean() ? 2 : -2);
			applyOptionsDelta(p);
		}
	}

	private void attachOptionsForElectronics(List<Electronics> items) {
		for (Product p : items) {
			ensureOptionsList(p);
			addOption(p, OptionType.ADD, 2 + rnd.nextInt(3));
			addOption(p, OptionType.SUB, 1 + rnd.nextInt(4));
			if (rnd.nextBoolean()) addOption(p, OptionType.ADJUSTMENT, rnd.nextBoolean() ? 1 : -1);
			applyOptionsDelta(p);
		}
	}

	private void attachOptionsForFood(List<Food> items) {
		for (Product p : items) {
			ensureOptionsList(p);
			addOption(p, OptionType.ADD, 10 + rnd.nextInt(25));
			addOption(p, OptionType.SUB, 5 + rnd.nextInt(20));
			addOption(p, OptionType.ADJUSTMENT, -(1 + rnd.nextInt(5)));
			applyOptionsDelta(p);
		}
	}

	private void attachOptionsForGeneric(List<Product> items) {
		for (Product p : items) {
			ensureOptionsList(p);
			addOption(p, OptionType.ADD, 3 + rnd.nextInt(8));
			addOption(p, OptionType.SUB, 2 + rnd.nextInt(6));
			if (rnd.nextInt(100) < 40) addOption(p, OptionType.ADJUSTMENT, rnd.nextBoolean() ? 1 : -1);
			applyOptionsDelta(p);
		}
	}

	private void ensureOptionsList(Product p) {
		if (p.getOptions() == null) p.setOptions(new ArrayList<>());
	}

	private void addOption(Product p, OptionType type, int magnitude) {
		Options o = new Options();
		o.setType(type);
		int change = switch (type) {
			case ADD -> Math.abs(magnitude);
			case SUB -> -Math.abs(magnitude);
			case ADJUSTMENT -> magnitude;
		};
		o.setQuantityChange(change);
		o.setProduct(p);
		p.getOptions().add(o);
	}

	private void applyOptionsDelta(Product p) {
		int delta = p.getOptions().stream().mapToInt(Options::getQuantityChange).sum();
		p.updateQuantity(delta);
	}
}
