package com.hiberus.controllers;

import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pizzas/read")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas(){
        List<Pizza> pizzas = pizzaService.getAllPizzas();
        if (pizzas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            return new ResponseEntity<>(pizzas, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long id){
        Pizza pizza = pizzaService.getPizzaById(id);
        return new ResponseEntity<>(pizza, HttpStatus.OK);

    }

}
