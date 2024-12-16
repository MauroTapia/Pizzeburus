package com.hiberus.config;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaWriteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigData {


    @Bean("ConfigData")
    CommandLineRunner commandLineRunner(PizzaWriteRepository pizzaRepository) {
        return args -> {
            // Comprobar si las pizzas ya existen en la base de datos
            if (!pizzaRepository.existsByName("FuggazetaWrite")) {
                Pizza pizza1 = Pizza.builder()
                        .name("FuggazetaWrite")
                        .build();
                pizzaRepository.save(pizza1);
            }

            if (!pizzaRepository.existsByName("MuzzarelaWrite")) {
                Pizza pizza2 = Pizza.builder()
                        .name("MuzzarelaWrite")
                        .build();
                pizzaRepository.save(pizza2);
            }

            if (!pizzaRepository.existsByName("JamonWrite")) {
                Pizza pizza3 = Pizza.builder()
                        .name("JamonWrite")
                        .build();
                pizzaRepository.save(pizza3);
            }
        };
    }
}