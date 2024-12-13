package com.hiberus.config;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigData {
    @Bean("ConfigData")
    CommandLineRunner commandLineRunner(PizzaRepository pizzaRepository) {
        return args -> {
            Pizza pizza1 = Pizza.builder()
                    .name("Fuggazeta")
                    .build();
            Pizza pizza2 = Pizza.builder()
                    .name("Muzzarela")
                    .build();
            Pizza pizza3 = Pizza.builder()
                    .name("Jamon")
                    .build();

            pizzaRepository.saveAll(List.of(pizza1,pizza2,pizza3));
        };
    }
}
