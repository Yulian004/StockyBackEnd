package com.generation.stockybackend.services;

import com.generation.stockybackend.model.dtos.options.OptionsInputDto;
import com.generation.stockybackend.model.dtos.options.OptionsOutputDto;
import com.generation.stockybackend.model.entities.Options;

import com.generation.stockybackend.model.entities.products.Product;
import com.generation.stockybackend.model.enums.OptionType;
import com.generation.stockybackend.model.repositories.OptionsRepository;

import com.generation.stockybackend.model.repositories.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Service
public class OptionsService
{
    @Autowired
    private OptionsRepository repo;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void applyOption(Options option)
    {
        Product product = option.getProduct();

        if(product == null)
            throw new RuntimeException("Product not found");

        int change = option.getQuantityChange();

        switch (option.getType())
        {
            case  ADD -> product.updateQuantity(change);
            case  SUB -> {
                if (change > product.getQuantity())
                    throw new RuntimeException("Insufficient Quantity");
                product.updateQuantity(-change);

            }
            case ADJUSTMENT -> {
                product.setQuantity(option.getQuantityChange());
                product.checkLevel();
            }
        }

        productRepository.save(product);
        repo.save(option);
    }


    public List<OptionsOutputDto> findAllAsDtos()
    {
        return repo.findAll().stream().map(this::convertToDto).toList();
    }

    public void saveWithDtoInput(OptionsInputDto dto)
    {
        Options e = convertToEntity(dto);
        repo.save(e);
    }

    private OptionsOutputDto convertToDto(Options e)
    {
        OptionsOutputDto res = new OptionsOutputDto();
        res.setId(e.getId());
        res.setTime(e.getTime());
        res.setQuantityChange(e.getQuantityChange());
        res.setType(e.getType());
        return res;
    }

    private Options convertToEntity(OptionsInputDto dto)
    {
        Options res = new Options();
        res.setTime(dto.getTime());
        res.setQuantityChange(dto.getQuantityChange());
        res.setType(dto.getType());

        return res;
    }

    public List<OptionsOutputDto> OperationsInTime (LocalDate day, LocalTime from, LocalTime to)
    {
        LocalDateTime start = day.atTime(from);
        LocalDateTime end = day.atTime(to);
        if (!end.isAfter(start)) end = end.plusDays(1);
        return repo.findOperationTimeBetween(start, end).stream().map(this::convertToDto).toList();

    }

    public List<OptionsOutputDto> getOperationUser(UUID id)
    {

       return repo.findOperationByUserId(id).stream().map(this::convertToDto).toList();
    }

    public List<OptionsOutputDto> TypeFilter (OptionType type)
    {
        return repo.findByType(type).stream().map(this::convertToDto).toList();
    }





}
