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
import org.springframework.web.server.ResponseStatusException;


import java.time.Duration;
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
    private PizzaService pizzaService;

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
    void testAddFavoritePizza_Success() {
        // Arrange
        Long userId = 1L;
        Long pizzaId = 2L;

        User user = new User();
        user.setFavoritePizzas(new ArrayList<Long>());

        Pizza pizza = new Pizza(pizzaId, "Pizza Margarita");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(pizzaService.obtenerPizza(pizzaId)).thenReturn(pizza);

        // Act
        Pizza result = userService.addFavoritePizza(userId, pizzaId);

        // Assert
        assertNotNull(result);
        assertEquals(pizzaId, result.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAddFavoritePizza_PizzaNotAvailable() {
        // Arrange
        Long userId = 1L;
        Long pizzaId = 2L;
        User user = new User();
        Pizza pizza = new Pizza(pizzaId, "Pizza no disponible");

        // Simular que el usuario existe
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // Simular que la pizza no está disponible
        when(pizzaService.obtenerPizza(pizzaId)).thenReturn(pizza);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.addFavoritePizza(userId, pizzaId));
    }
}