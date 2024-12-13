package com.hiberus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApplicationGatewayServer {
    public static void main(final String[] args) {
        SpringApplication.run(ApplicationGatewayServer.class, args);
    }
}
