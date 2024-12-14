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
            Pizza pizza1 = Pizza.builder()
                    .name("FuggazetaWrite")
                    .build();
            Pizza pizza2 = Pizza.builder()
                    .name("MuzzarelaWrite")
                    .build();
            Pizza pizza3 = Pizza.builder()
                    .name("JamonWrite")
                    .build();

            pizzaRepository.saveAll(List.of(pizza1,pizza2,pizza3));
        };
    }
}
