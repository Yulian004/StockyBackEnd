package com.generation.stockybackend.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Product extends BaseEntity{
    private double weight;
    private double volume;
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Section section;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Options> options = new HashSet<>();

    public void addOption(Options option)
    {
        options.add(option);
        option.setProduct(this);
    }
}
