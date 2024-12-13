package com.hiberus.config;


import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigData {
    @Bean("ConfigData")
    CommandLineRunner commandLineRunner(UserRepository repositorioUsuario) {
        return args -> {
            User usuario1 =User.builder()
                    .name("Mauro")
                    .pizzafav("Fugazzeta")
                    .build();

            User usuario2 =User.builder()
                    .name("Emilio")
                    .pizzafav("Muzzarela")
                    .build();

            User usuario3 =User.builder()
                    .name("Tomas")
                    .pizzafav("Napolitana")
                    .build();

            repositorioUsuario.saveAll(List.of(usuario1, usuario2,usuario3));
        };
    }
}
