package com.hiberus.services;

import com.hiberus.models.Pizza;
import com.hiberus.models.dto.PizzaDTO;
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
        PizzaDTO pizza = new PizzaDTO();
        pizza.setName("Margherita");
        pizzaWriteService.createPizza(pizza);
    }
    @Test
    void shouldCreatePizza() {
        // Arrange
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setName("Margarita");

        Pizza expectedPizza = new Pizza();
        expectedPizza.setName("Margarita");

        when(pizzaWriteRepository.save(any(Pizza.class))).thenReturn(expectedPizza);

        // Act
        Pizza createdPizza = pizzaWriteService.createPizza(pizzaDTO);

        // Assert
        assertNotNull(createdPizza, "The created pizza should not be null");
        assertEquals("Margarita", createdPizza.getName(), "The pizza name should match");
        verify(pizzaWriteRepository, times(1)).save(any(Pizza.class));
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
        assertNotNull(foundPizza, "The pizza should not be null when found by ID");
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

        // Crear el objeto PizzaDTO con el nombre que se va a actualizar
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setName("Pepperoni");

        // Crear el objeto Pizza que simula el registro en la base de datos
        Pizza pizzaToUpdate = new Pizza();
        pizzaToUpdate.setId(pizzaId);
        pizzaToUpdate.setName("Margarita");

        // Crear el objeto Pizza que será el resultado esperado de la actualización
        Pizza pizzaUpdated = new Pizza();
        pizzaUpdated.setId(pizzaId);  // El id sigue siendo el mismo
        pizzaUpdated.setName("Pepperoni");

        // Simulamos que el repositorio devuelve la pizza original y la guarda después de la actualización
        when(pizzaWriteRepository.findById(pizzaId)).thenReturn(Optional.of(pizzaToUpdate));
        when(pizzaWriteRepository.save(pizzaToUpdate)).thenReturn(pizzaUpdated);

        // Act
        Pizza updatedPizza = pizzaWriteService.updatePizza(pizzaId, pizzaDTO); // Pasamos el id y el DTO

        // Assert
        assertNotNull(updatedPizza);
        assertEquals("Pepperoni", updatedPizza.getName());
        assertEquals(pizzaId, updatedPizza.getId());  // Verificamos que el id no cambió
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
