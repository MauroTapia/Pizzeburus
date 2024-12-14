package com.hiberus.services.Impl;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import com.hiberus.services.PizzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    private static final Logger logger = LoggerFactory.getLogger(PizzaServiceImpl.class);

    @Autowired
    PizzaRepository pizzaRepository;


    @Override
    public List<Pizza> getAllPizzas() {
        logger.info("Obteniendo todas las pizzas");  // Log de información
        List<Pizza> pizzas = pizzaRepository.findAll();
        logger.debug("Pizzas encontradas: {}", pizzas);  // Log de debug (muestra el contenido de pizzas)
        return pizzas;
    }

    @Override
    public Pizza getPizzaById(Long id) {
        logger.info("Buscando pizza con ID: {}", id);  // Log de información
        Optional<Pizza> optionalPizza = pizzaRepository.findById(id);

        if (optionalPizza.isPresent()) {
            logger.info("Pizza encontrada: {}", optionalPizza.get());  // Log de información
            return optionalPizza.get();
        } else {
            logger.error("No se encontró pizza con ID: {}", id);  // Log de error
            return null;
        }
    }
}
