package com.hiberus.controllers;

import com.hiberus.models.Pizza;
import com.hiberus.services.Impl.PizzaServiceImpl;
import com.hiberus.services.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PizzaControllerTest {

    @Mock
    private PizzaService pizzaService;

    @InjectMocks
    private PizzaController pizzaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pizzaController).build();
    }

    @Test
    void testGetAllPizzas_ReturnsListOfPizzas() throws Exception {
        List<Pizza> mockPizzas = List.of(
                new Pizza(1L, "Margarita"),
                new Pizza(2L, "Pepperoni")
        );

        when(pizzaService.getAllPizzas()).thenReturn(mockPizzas);

        mockMvc.perform(get("/pizzas/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Margarita"));

        verify(pizzaService, times(1)).getAllPizzas();
    }

    @Test
    void testGetPizzaById_ReturnsPizza() throws Exception {
        Pizza mockPizza = new Pizza(1L, "Margarita");

        when(pizzaService.getPizzaById(1L)).thenReturn(mockPizza);

        mockMvc.perform(get("/pizzas/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Margarita"));

        verify(pizzaService, times(1)).getPizzaById(1L);
    }

    @Test
    void testGetPizzaById_ReturnsNotFound_WhenPizzaDoesNotExist() throws Exception {
        when(pizzaService.getPizzaById(99L)).thenReturn(null);

        mockMvc.perform(get("/pizzas/read/99"))
                .andExpect(status().isNotFound());

        verify(pizzaService, times(1)).getPizzaById(99L);
    }


}
