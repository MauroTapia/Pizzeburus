package com.hiberus.services.Impl;


import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.feignConfig.FeignPizzasRead;
import com.hiberus.models.Pizza;
import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

    @Service
    public class UserServiceImpl implements UserServices {

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
        public User createUser(User user) {
            return userRepository.save(user);
        }

        @Override
        public User updateUser(Long id, User userDetails) {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));

            existingUser.setName(userDetails.getName());
            existingUser.setFavoritePizzas(userDetails.getFavoritePizzas());

            return userRepository.save(existingUser);
        }

        @Override
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }

        @Override
        public void addFavoritePizza(Long userId, Long pizzaId) {
            Pizza pizza = feignPizzasRead.getPizzaById(pizzaId);
            if (pizza == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza no encontrada");
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            user.getFavoritePizzas().add(pizzaId);
            userRepository.save(user);
        }

        @Override
        public void removeFavoritePizza(Long userId, Long pizzaId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            if (!user.getFavoritePizzas().remove(pizzaId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La pizza no estaba en favoritos");
            }

            userRepository.save(user);
        }
    }
