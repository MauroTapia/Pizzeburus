package com.hiberus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@Configuration
//@EnableSwagger2
//public class ConfigSwagger {
//
//    private static final String TITLE = "Users API";
//    private static final String DESCRIPTION = "API para la gestión de usuarios";
//    private static final String BASE_PACKAGE = "com.hiberus.controllers";
//    private static final String VERSION = "1.0";
//
//    @Bean
//    public Docket swagger() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(TITLE)
//                .description(DESCRIPTION)
//                .version(VERSION)
//                .build();
//    }
//}