package com.hiberus.services;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import com.hiberus.services.Impl.PizzaServiceImpl;
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
public class PizzaReadTestUnitarios {
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

        assertNotNull(pizzas);
        assertEquals(2, pizzas.size());
        assertEquals("Muzzarella", pizzas.get(0).getName());
        assertEquals("Peperoni", pizzas.get(1).getName());

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
