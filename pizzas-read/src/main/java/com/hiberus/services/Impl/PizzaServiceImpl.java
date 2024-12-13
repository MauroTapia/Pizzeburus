package com.hiberus.services.Impl;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import com.hiberus.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;


    @Override
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza getPizzaById(Long id) {
        Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
        return optionalPizza.orElse(null);
    }
}
