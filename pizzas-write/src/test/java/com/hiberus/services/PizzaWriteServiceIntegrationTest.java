package com.hiberus.services;

import com.hiberus.models.Pizza;
import com.hiberus.models.dto.PizzaDTO;
import com.hiberus.repositories.PizzaWriteRepository;
import com.hiberus.services.Impl.PizzaWriteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PizzaWriteServiceIntegrationTest {
    @Autowired
    PizzaWriteRepository pizzaWriteRepository;

    @Autowired
    PizzaWriteServiceImpl pizzaWriteService;
    @Test
    void shouldCreatePizza() {
        // Arrange
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setName("Margarita");

        // Act
        Pizza createdPizza = pizzaWriteService.createPizza(pizzaDTO);

        // Assert
        assertNotNull(createdPizza, "The created pizza should not be null");
        assertEquals("Margarita", createdPizza.getName(), "The pizza name should match");
    }

    @Test
    void shouldGetPizzaById_Found() {
        // Arrange
        Pizza pizza = new Pizza();
        pizza.setName("Margarita");
        pizzaWriteRepository.save(pizza);

        // Act
        Pizza foundPizza = pizzaWriteService.getPizzaById(pizza.getId());

        // Assert
        assertNotNull(foundPizza, "The pizza should not be null when found by ID");
        assertEquals(pizza.getId(), foundPizza.getId());
        assertEquals("Margarita", foundPizza.getName());
    }

    @Test
    void shouldGetPizzaById_NotFound() {
        // Arrange
        Long pizzaId = 999L;

        // Act
        Pizza foundPizza = pizzaWriteService.getPizzaById(pizzaId);

        // Assert
        assertNull(foundPizza, "The pizza should be null when not found by ID");
    }

    @Test
    void shouldUpdatePizza() {
        // Arrange
        Pizza pizzaToUpdate = new Pizza();
        pizzaToUpdate.setName("Margarita");
        pizzaWriteRepository.save(pizzaToUpdate); // Guardamos una pizza en la base de datos

        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setName("Pepperoni");

        // Act
        Pizza updatedPizza = pizzaWriteService.updatePizza(pizzaToUpdate.getId(), pizzaDTO);

        // Assert
        assertNotNull(updatedPizza);
        assertEquals("Pepperoni", updatedPizza.getName());
        assertEquals(pizzaToUpdate.getId(), updatedPizza.getId());
    }

    @Test
    void shouldDeletePizza() {
        // Arrange
        Pizza pizzaToDelete = new Pizza();
        pizzaToDelete.setName("Margarita");
        pizzaWriteRepository.save(pizzaToDelete); // Guardamos una pizza en la base de datos

        Long pizzaId = pizzaToDelete.getId();

        // Act
        pizzaWriteService.deletePizza(pizzaId);

        // Assert
        Optional<Pizza> deletedPizza = pizzaWriteRepository.findById(pizzaId);
        assertTrue(deletedPizza.isEmpty(), "The pizza should be deleted from the database");
    }
}