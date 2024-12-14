package com.hiberus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ApplicationPizzasRead {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationPizzasRead.class,args);
    }
}