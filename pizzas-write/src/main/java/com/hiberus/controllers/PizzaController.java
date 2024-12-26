package com.hiberus.controllers;

import com.hiberus.models.Pizza;
import com.hiberus.models.dto.PizzaDTO;
import com.hiberus.services.PizzaWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/pizzas/write")
public class PizzaController {

    @Autowired
    private PizzaWriteService pizzaService;

    @Operation(
            summary = "Create a new pizza",
            description = "Creates a pizza with the given details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pizza.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data")
    })
    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody PizzaDTO pizzaDTO){
        Pizza pizzaCreated = pizzaService.createPizza(pizzaDTO);
        return new ResponseEntity<>(pizzaCreated, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a pizza by ID",
            description = "Deletes a pizza with the given ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pizza not found with the given ID")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePizzaById(@PathVariable Long id) {
        Pizza pizza = pizzaService.getPizzaById(id);
        if (pizza != null) {
            pizzaService.deletePizza(id);
            return new ResponseEntity<>("Pizza deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Pizza not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Update a pizza by ID",
            description = "Updates the pizza details for the given ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pizza.class))),
            @ApiResponse(responseCode = "404", description = "Pizza not found with the given ID")
    })
    @PatchMapping("{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable("id") Long id, @RequestBody PizzaDTO pizzaDTO) {
        // Aquí el id viene de la URL y el name del cuerpo de la solicitud
        Pizza updatedPizza = pizzaService.updatePizza(id, pizzaDTO);
        return new ResponseEntity<>(updatedPizza, HttpStatus.OK);
    }
}
