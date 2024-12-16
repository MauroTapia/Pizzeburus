package com.hiberus.controllers;

import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pizzas/write")
public class PizzaController {

    @Autowired
    private PizzaWriteService pizzaService;

    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza){
        Pizza pizzaCreated = pizzaService.createPizza(pizza);
        return new ResponseEntity<>(pizzaCreated, HttpStatus.OK);
    }

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

    @PatchMapping("{id}")
    public ResponseEntity<Pizza> updateNotebook(@PathVariable("id") Long id, @RequestBody Pizza pizza) {
        pizza.setId(id);
        Pizza updatePizza = pizzaService.updatePizza(pizza);
        return new ResponseEntity<>(updatePizza, HttpStatus.OK);
    }
}
