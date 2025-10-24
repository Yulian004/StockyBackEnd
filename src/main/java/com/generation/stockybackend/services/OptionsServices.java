package com.generation.stockybackend.services;

import com.generation.stockybackend.model.entities.Product;
import com.generation.stockybackend.model.repositories.OptionsRepository;
import com.generation.stockybackend.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class OptionsServices {
    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private ProductRepository productRepository;

    public void AddQuantity(UUID id, int quantityToAdd) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

    }
}
