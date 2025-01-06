package com.hiberus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ApplicationUsers {
    private static final Logger logger = LogManager.getLogger(ApplicationUsers.class);
    public static void main(String[] args) {
        logger.info("Este es un mensaje de prueba para los logs.");
        SpringApplication.run(ApplicationUsers.class,args);
    }
}