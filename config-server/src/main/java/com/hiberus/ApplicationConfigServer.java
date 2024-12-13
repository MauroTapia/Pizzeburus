package com.hiberus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude={SolrAutoConfiguration.class})
@EnableConfigServer
@EnableEurekaClient
public class ApplicationConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfigServer.class, args);
    }
}