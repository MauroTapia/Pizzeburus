package com.hiberus.services.Impl;


import com.hiberus.controllers.UserController;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.feignConfig.FeignPizzasRead;
import com.hiberus.models.Pizza;
import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.UserServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

    @Service
    public class UserServiceImpl implements UserServices {

        private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

        @Autowired
         FeignPizzasRead feignPizzasRead;
         @Autowired
         UserRepository userRepository;

        @Override
        public List<User> getUsers() {
            return userRepository.findAll();
        }

        @Override
        public User getUserById(Long id) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        @Override
        public User createUser(CreateUserDto createUserDto) {
            if (createUserDto == null || createUserDto.getName() == null || createUserDto.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("User name cannot be null or empty");
            }

            User user = User.builder()
                    .name(createUserDto.getName())
                    .build();

            return userRepository.save(user);
        }

        @Override
        public User updateUser(Long id, CreateUserDto updateUserDto) {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));

            existingUser.setName(updateUserDto.getName());
            return userRepository.save(existingUser);
        }

        @Override
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }

        @Override
        public void addFavoritePizza(Long userId, Long pizzaId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            if (user.getFavoritePizzas().contains(pizzaId)) {
//                log.warn("La pizza {} ya está en los favoritos del usuario {}", pizzaId, userId);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La pizza ya está en favoritos");
            }

            Pizza pizza = getPizzaByIdWithCircuitBreaker(pizzaId);

            user.getFavoritePizzas().add(pizzaId);

            userRepository.save(user);
//            log.info("Pizza {} agregada a los favoritos del usuario {}", pizzaId, userId);
        }

        @Override
        public void removeFavoritePizza(Long userId, Long pizzaId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            if (!user.getFavoritePizzas().contains(pizzaId)) {
//                log.warn("La pizza {} no está en los favoritos del usuario {}", pizzaId, userId);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La pizza no estaba en favoritos");
            }

            Pizza pizza = getPizzaByIdWithCircuitBreaker(pizzaId);

            user.getFavoritePizzas().remove(pizzaId);

            userRepository.save(user);
//            log.info("Pizza {} eliminada de los favoritos del usuario {}", pizzaId, userId);
        }

        @CircuitBreaker(name = "pizzasReadService", fallbackMethod = "fallbackPizzaNotFound")
        public Pizza getPizzaByIdWithCircuitBreaker(Long pizzaId) {
            return feignPizzasRead.getPizzaById(pizzaId);
        }

        public void fallbackPizzaNotFound(Long pizzaId, Throwable throwable) {
//            log.error("Error al intentar obtener la pizza {}. Causado por: {}", pizzaId, throwable.getMessage());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El servicio de pizzas no está disponible, intente más tarde.");
        }

    }
