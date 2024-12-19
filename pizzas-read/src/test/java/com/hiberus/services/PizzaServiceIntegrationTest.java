package com.hiberus.services;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import com.hiberus.services.Impl.PizzaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PizzaServiceIntegrationTest {
    @Autowired
    private PizzaServiceImpl pizzaService;

    @Autowired
    private PizzaRepository pizzaRepository;

    @BeforeEach
    void setup() {
        pizzaRepository.deleteAll();
    }

    @Test
    void shouldGetAllPizzas() {

        Pizza pizza1 = Pizza.builder().name("Muzzarella").build();
        Pizza pizza2 = Pizza.builder().name("Peperoni").build();
        pizzaRepository.save(pizza1);
        pizzaRepository.save(pizza2);

        List<Pizza> pizzas = pizzaService.getAllPizzas();

        assertNotNull(pizzas);
        assertEquals(2, pizzas.size());
        assertEquals("Muzzarella", pizzas.get(0).getName());
        assertEquals("Peperoni", pizzas.get(1).getName());
    }

    @Test
    void shouldReturnPizzaById() {
        Pizza pizza = Pizza.builder().name("Muzzarella").build();
        Pizza savedPizza = pizzaRepository.save(pizza);

        Pizza result = pizzaService.getPizzaById(savedPizza.getId());

        assertNotNull(result, "La pizza no debe ser nula");
        assertEquals("Muzzarella", result.getName(), "El nombre de la pizza debería ser Muzzarella");
    }
}
