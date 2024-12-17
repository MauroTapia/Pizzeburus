package com.hiberus.service;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import com.hiberus.services.Impl.PizzaServiceImpl;
import com.hiberus.services.PizzaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PizzaReadTest {
    @Mock
    PizzaRepository pizzaRepository;

    @InjectMocks
    PizzaServiceImpl pizzaService;

    @Test
    void ShouldGetAllPizzas(){
        List<Pizza> mockPizzas = List.of(
                Pizza.builder()
                        .id(1L)
                        .name("Muzzarella")
                        .build(),
                Pizza.builder()
                        .id(2L)
                        .name("Peperoni")
                        .build()
        );

        when(pizzaRepository.findAll()).thenReturn(mockPizzas);

        List<Pizza> pizzas   = pizzaService.getAllPizzas();

        assertNotNull(pizzas); // Verifica que la lista no sea nula
        assertEquals(2, pizzas.size()); // Verifica que la lista tiene el tamaño esperado
        assertEquals("Muzzarella", pizzas.get(0).getName()); // Verifica el nombre de la primera pizza
        assertEquals("Peperoni", pizzas.get(1).getName()); // Verifica el nombre de la segunda pizza

        verify(pizzaRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserById() {
        // Arrange
        Long pizzaId = 1L;
        Pizza mockPizza = Pizza.builder().id(pizzaId).name("Muzzarella").build();
        when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.of(mockPizza));

        // Act
        Pizza user = pizzaService.getPizzaById(pizzaId);

        // Assert
        assertEquals("Muzzarella", user.getName());
        verify(pizzaRepository, times(1)).findById(pizzaId);
    }

}
