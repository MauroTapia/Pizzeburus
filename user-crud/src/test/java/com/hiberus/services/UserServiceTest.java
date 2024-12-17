package com.hiberus.services;

import com.hiberus.feignConfig.FeignPizzasRead;
import com.hiberus.models.Pizza;
import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private FeignPizzasRead feignPizzasRead;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnAllUsers() {
        // Arrange
        List<User> mockUsers = List.of(
                User.builder()
                        .id(1L)
                        .name("John")
                        .favoritePizzas(List.of(101L, 102L))
                        .build(),
                User.builder()
                        .id(2L)
                        .name("Jane")
                        .favoritePizzas(List.of(103L))
                        .build()
        );

        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<User> users = userService.getUsers();

        // Assert
        assertEquals(2, users.size());
        assertEquals(2, users.get(0).getFavoritePizzas().size());
        assertEquals(1, users.get(1).getFavoritePizzas().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }


    @Test
    void shouldReturnUserById() {
        // Arrange
        Long userId = 1L;
        User mockUser = User.builder().id(userId).name("John").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        User user = userService.getUserById(userId);

        // Assert
        assertEquals("John", user.getName());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldCreateUser() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setName("John");

        User savedUser = User.builder().id(1L).name("John").build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User createdUser = userService.createUser(dto);

        // Assert
        assertEquals("John", createdUser.getName());
        assertEquals(1L, createdUser.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }
    @Test
    void shouldAddFavoritePizza() {
        // Arrange
        Long userId = 1L;
        Long pizzaId = 1L;

        User user = User.builder().id(userId).name("John").favoritePizzas(new ArrayList<>()).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Pizza mockPizza = new Pizza(1L, "MuzzarelaWrite");
        when(feignPizzasRead.getPizzaById(pizzaId)).thenReturn(mockPizza);

        // Act
        userService.addFavoritePizza(userId, pizzaId);

        // Assert
        assertTrue(user.getFavoritePizzas().contains(pizzaId));

        verify(userRepository, times(1)).save(user);

        verify(feignPizzasRead, times(1)).getPizzaById(pizzaId);
    }

    @Test
    void shouldRemoveFavoritePizza_Success() {
        // Arrange
        Long userId = 1L;
        Long pizzaId = 20L;

        List<Long> favoritePizzas = new ArrayList<>(Arrays.asList(10L, 20L, 30L));
        User user = User.builder().id(userId).name("John").favoritePizzas(favoritePizzas).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.removeFavoritePizza(userId, pizzaId);

        // Assert
        assertFalse(user.getFavoritePizzas().contains(pizzaId));
        verify(userRepository, times(1)).save(user);
    }

}