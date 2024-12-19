package com.hiberus.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigSwagger {

    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("write-pizzas")
                .pathsToMatch("/pizzas/write/**")
                .build();
    }
}
