package com.hiberus.services.Impl;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaWriteRepository;
import com.hiberus.services.PizzaWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaWriteServiceImpl implements PizzaWriteService {

    @Autowired
    PizzaWriteRepository pizzaWriteRepository;


    @Override
    public Pizza createPizza(Pizza pizza) {
        return pizzaWriteRepository.save(pizza);
    }

    @Override
    public Pizza getPizzaById(Long id) {
        Optional<Pizza> optionalPizza = pizzaWriteRepository.findById(id);
        return optionalPizza.orElse(null);
    }

    @Override
    public Pizza updatePizza(Pizza pizza) {
        Pizza pizzaUpdate = pizzaWriteRepository.findById(pizza.getId()).get();
        pizzaUpdate.setName(pizza.getName());
        Pizza pizzaUpdateSave = pizzaWriteRepository.save(pizzaUpdate);
        return pizzaUpdateSave;
    }


    @Override
    public void deletePizza(Long id) {
        pizzaWriteRepository.deleteById(id);
    }
}
