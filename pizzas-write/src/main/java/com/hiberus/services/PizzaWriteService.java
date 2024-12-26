package com.hiberus.services;

import com.hiberus.models.Pizza;
import com.hiberus.models.dto.PizzaDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PizzaWriteService {

    Pizza createPizza(PizzaDTO pizzaDTO);
    Pizza getPizzaById(Long id);
    void deletePizza(Long id);
    Pizza updatePizza(Long id, PizzaDTO pizzaDTO);
}
