package com.hiberus.controllers;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hiberus.models.Pizza;
import com.hiberus.models.dto.PizzaDTO;
import com.hiberus.services.PizzaWriteService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PizzaControllerTest {

    @Mock
    private PizzaWriteService pizzaService;

    @InjectMocks
    private PizzaController pizzaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pizzaController).build();
    }

    @Test
    public void testCreatePizza() throws Exception {
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setName("Pepperoni");

        Pizza pizzaCreated = new Pizza(125L, "Pepperoni");

        when(pizzaService.createPizza(any(PizzaDTO.class))).thenReturn(pizzaCreated);

        mockMvc.perform(post("/pizzas/write")
                        .contentType("application/json")
                        .content("{\"name\": \"Pepperoni\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pepperoni"));

        verify(pizzaService, times(1)).createPizza(any(PizzaDTO.class));
    }

    @Test
    public void testDeletePizza() throws Exception {
        Pizza pizza = new Pizza(1L, "Pepperoni");

        // Mockeamos el comportamiento del servicio para obtener la pizza
        when(pizzaService.getPizzaById(1L)).thenReturn(pizza);

        // Realizamos una petición DELETE para eliminar la pizza
        mockMvc.perform(delete("/pizzas/write/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Pizza deleted"));

        // Verificamos que el servicio fue llamado para eliminar la pizza
        verify(pizzaService, times(1)).deletePizza(1L);
    }

    @Test
    public void testDeletePizzaNotFound() throws Exception {
        // Mockeamos el comportamiento del servicio para no encontrar la pizza
        when(pizzaService.getPizzaById(1L)).thenReturn(null);

        // Realizamos una petición DELETE para eliminar una pizza que no existe
        mockMvc.perform(delete("/pizzas/write/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pizza not found"));

        // Verificamos que el servicio fue llamado para verificar la pizza
        verify(pizzaService, times(1)).getPizzaById(1L);
    }

    @Test
    public void testUpdatePizza() throws Exception {
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setName("Vegetariana");

        Pizza pizzaUpdated = new Pizza(1L, "Vegetariana");

        // Mockeamos el comportamiento del servicio para actualizar la pizza
        when(pizzaService.updatePizza(eq(1L), any(PizzaDTO.class))).thenReturn(pizzaUpdated);

        // Realizamos una petición PATCH para actualizar la pizza
        mockMvc.perform(patch("/pizzas/write/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"name\": \"Vegetariana\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vegetariana"));

        // Verificamos que el servicio fue llamado para actualizar la pizza
        verify(pizzaService, times(1)).updatePizza(eq(1L), any(PizzaDTO.class));
    }
}
