package com.hiberus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ApplicationPizzas {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationPizzas.class,args);
    }
}