package com.hiberus.services.Impl;

import com.hiberus.models.Pizza;
import com.hiberus.models.dto.PizzaDTO;
import com.hiberus.repositories.PizzaWriteRepository;
import com.hiberus.services.PizzaWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaWriteServiceImpl implements PizzaWriteService {

    @Autowired
    PizzaWriteRepository pizzaWriteRepository;


    @Override
    public Pizza createPizza(PizzaDTO pizzaDTO) {
        if (pizzaDTO == null || pizzaDTO.getName() == null || pizzaDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Pizza name cannot be null or empty");
        }

        Pizza pizza = new Pizza();
        pizza.setName(pizzaDTO.getName());

        return pizzaWriteRepository.save(pizza);
    }

    @Override
    public Pizza getPizzaById(Long id) {
        Optional<Pizza> optionalPizza = pizzaWriteRepository.findById(id);
        return optionalPizza.orElse(null);
    }

    @Override
    public Pizza updatePizza(Long id, PizzaDTO pizzaDTO) {
        Pizza pizzaToUpdate = pizzaWriteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found with ID: " + id));

        pizzaToUpdate.setName(pizzaDTO.getName());

        return pizzaWriteRepository.save(pizzaToUpdate);
    }


    @Override
    public void deletePizza(Long id) {
        pizzaWriteRepository.deleteById(id);
    }
}
