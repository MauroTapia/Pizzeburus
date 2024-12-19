package com.hiberus.services;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaWriteRepository;
import com.hiberus.services.Impl.PizzaWriteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PizzasWriteServiceTest {

    @Mock
    PizzaWriteRepository pizzaWriteRepository;

    @InjectMocks
    PizzaWriteServiceImpl pizzaWriteService;

    @Test
    void coverageTest() {
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        pizza.setName("Margherita");
        pizzaWriteService.createPizza(pizza);  // Esto debe cubrir el método createPizza
    }
    @Test
    void shouldCreatePizza() {
        // Arrange
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        pizza.setName("Margarita");

        when(pizzaWriteRepository.save(pizza)).thenReturn(pizza);

        // Act
        Pizza createdPizza = pizzaWriteService.createPizza(pizza);

        // Assert
        assertNotNull(createdPizza);
        assertEquals("Margarita", createdPizza.getName());
        verify(pizzaWriteRepository, times(1)).save(pizza);
    }

    @Test
    void shouldGetPizzaById_Found() {
        // Arrange
        Long pizzaId = 1L;
        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName("Margarita");

        when(pizzaWriteRepository.findById(pizzaId)).thenReturn(Optional.of(pizza));

        // Act
        Pizza foundPizza = pizzaWriteService.getPizzaById(pizzaId);

        // Assert
        assertNotNull(foundPizza);
        assertEquals(pizzaId, foundPizza.getId());
        assertEquals("Margarita", foundPizza.getName());
        verify(pizzaWriteRepository, times(1)).findById(pizzaId);
    }

    @Test
    void shouldGetPizzaById_NotFound() {
        // Arrange
        Long pizzaId = 1L;

        when(pizzaWriteRepository.findById(pizzaId)).thenReturn(Optional.empty());

        // Act
        Pizza foundPizza = pizzaWriteService.getPizzaById(pizzaId);

        // Assert
        assertNull(foundPizza);
        verify(pizzaWriteRepository, times(1)).findById(pizzaId);
    }

    @Test
    void shouldUpdatePizza() {
        // Arrange
        Long pizzaId = 1L;
        Pizza pizzaToUpdate = new Pizza();
        pizzaToUpdate.setId(pizzaId);
        pizzaToUpdate.setName("Margarita");

        Pizza pizzaUpdated = new Pizza();
        pizzaUpdated.setId(pizzaId);
        pizzaUpdated.setName("Pepperoni");

        when(pizzaWriteRepository.findById(pizzaId)).thenReturn(Optional.of(pizzaToUpdate));
        when(pizzaWriteRepository.save(pizzaToUpdate)).thenReturn(pizzaUpdated);

        // Act
        Pizza updatedPizza = pizzaWriteService.updatePizza(pizzaUpdated);

        // Assert
        assertNotNull(updatedPizza); //Si no es nullo tira success
        assertEquals("Pepperoni", updatedPizza.getName());
        verify(pizzaWriteRepository, times(1)).findById(pizzaId);
        verify(pizzaWriteRepository, times(1)).save(pizzaToUpdate);
    }

    @Test
    void shouldDeletePizza() {
        // Arrange
        Long pizzaId = 1L;

        // Act
        pizzaWriteService.deletePizza(pizzaId);

        // Assert
        verify(pizzaWriteRepository, times(1)).deleteById(pizzaId);
    }
}
