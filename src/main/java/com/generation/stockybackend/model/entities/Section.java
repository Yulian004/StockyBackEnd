package com.generation.stockybackend.model.entities;

import com.generation.stockybackend.model.entities.products.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Section extends BaseEntity{
    private String category;
    private String room;
    private int number;
    private double maxWeight;
    private double maxVolume;

	@OneToMany(mappedBy = "section", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet<>();


    public void addProduct(Product product)
    {
        products.add(product);
        product.setSection(this);
    }

    public double occupiedWeight() {
        double pTot = 0;
        for (Product p:products){
            pTot+=p.getWeight();
        }
        return pTot;
    }
    public double occupiedVolume() {
        double pTot = 0;
        for (Product p:products){
            pTot+=p.getVolume();
        }
        return pTot;
    }
    public double availableWeight() {
        double pTot = 0;
        for (Product p:products){
            pTot+=p.getWeight();
        }
        return maxWeight-pTot;
    }
    public double availableVolume() {
        double pTot = 0;
        for (Product p:products){
            pTot+=p.getVolume();
        }
        return maxVolume-pTot;
    }
}
