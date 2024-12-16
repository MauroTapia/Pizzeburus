package com.hiberus.services;

import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;

import java.util.List;

public interface UserServices {
    List<User> getUsers();
    User getUserById(Long id);
    User createUser(CreateUserDto createUserDto);
    User updateUser(Long id, CreateUserDto updateUserDto);
    void deleteUser(Long id);
    void addFavoritePizza(Long userId, Long pizzaId);

    void removeFavoritePizza(Long userId, Long pizzaId);

}
