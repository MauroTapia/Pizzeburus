package com.hiberus.services.Impl;

import com.hiberus.feignConfig.FeignPizzasRead;
import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("feign-pizzas")
@AllArgsConstructor
@Slf4j
public class PizzaServiceImpl implements PizzaService {
    private FeignPizzasRead feignPizzasRead;

    @CircuitBreaker(name = "pizzas-read",fallbackMethod = "fallBackObtenerPizza")
    @Override
    public Pizza obtenerPizza(Long id) {
        return feignPizzasRead.getPizzaById(id);
    }

    private Pizza fallBackObtenerPizza(Throwable throwable) {
        return new Pizza(0L,"Pizza no disponible");
    }

}
