package com.hiberus.controllers;

import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/pizzas/read")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Operation(
            summary = "Get all pizzas",
            description = "Fetches a list of all available pizzas in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of pizzas"),
            @ApiResponse(responseCode = "404", description = "No pizzas found")
    })
    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas(){
        List<Pizza> pizzas = pizzaService.getAllPizzas();
        if (pizzas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            return new ResponseEntity<>(pizzas, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Get pizza by ID",
            description = "Fetches the pizza details by its unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the pizza", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pizza.class))),
            @ApiResponse(responseCode = "404", description = "Pizza not found with the given ID")
    })
    @GetMapping("{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long id){
        Pizza pizza = pizzaService.getPizzaById(id);
        return new ResponseEntity<>(pizza, HttpStatus.OK);

    }



}
