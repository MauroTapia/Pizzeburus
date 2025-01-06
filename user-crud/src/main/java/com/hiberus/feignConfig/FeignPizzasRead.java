package com.hiberus.feignConfig;

import com.hiberus.models.Pizza;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "pizzas-read", url = "http://pizzas-read:8081/pizzas/read")
public interface  FeignPizzasRead {

    @GetMapping("/{id}")
    Pizza getPizzaById(@PathVariable("id") Long id);
}