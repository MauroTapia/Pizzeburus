package com.hiberus.services;

import com.hiberus.models.Pizza;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PizzaService {

    List<Pizza> getAllPizzas();
    Pizza getPizzaById(Long id);
}
