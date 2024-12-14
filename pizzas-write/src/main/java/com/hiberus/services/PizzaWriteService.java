package com.hiberus.services;

import com.hiberus.models.Pizza;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PizzaWriteService {

    Pizza createPizza(Pizza pizza);
    Pizza getPizzaById(Long id);

    Pizza updatePizza(Pizza pizza);
    void deletePizza(Long id);
}
