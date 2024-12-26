package com.hiberus.services;

import com.hiberus.feignConfig.FeignPizzasRead;
import com.hiberus.models.Pizza;
import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.Impl.UserServiceImpl;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeignPizzasRead feignPizzasRead;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void shouldReturnAllUsers() {
        // Arrange
        User user1 = User.builder()
                .name("John")
                .favoritePizzas(List.of(101L, 102L))
                .build();
        User user2 = User.builder()
                .name("Jane")
                .favoritePizzas(List.of(103L))
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        List<User> users = userService.getUsers();

        users.forEach(user -> Hibernate.initialize(user.getFavoritePizzas()));
        // Assert
        assertEquals(2, users.size());
        assertEquals(2, users.get(0).getFavoritePizzas().size());
        assertEquals(1, users.get(1).getFavoritePizzas().size());
    }

    @Test
    void shouldDeleteUser() {
        // Arrange
        User user = User.builder().name("John").build();
        User savedUser = userRepository.save(user);

        // Act
        userService.deleteUser(savedUser.getId());

        // Assert
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void shouldReturnUserById() {
        // Arrange
        User user = User.builder().name("John").build();
        User savedUser = userRepository.save(user);

        // Act
        User foundUser = userService.getUserById(savedUser.getId());

        // Assert
        assertEquals("John", foundUser.getName());
    }

    @Test
    void shouldCreateUser() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setName("John");

        // Act
        User createdUser = userService.createUser(dto);

        // Assert
        assertNotNull(createdUser.getId());
        assertEquals("John", createdUser.getName());
    }

    //debe estar read-pizza levantado

    @Test
    @Transactional
    void shouldAddFavoritePizza_Success() {
        // Arrange
        Long userId = 1L;
        Long pizzaId = 1L;

        List<Long> favoritePizzas = new ArrayList<>(Arrays.asList(10L, 20L, 30L));
        User user = User.builder()
                .name("John")
                .favoritePizzas(favoritePizzas)
                .build();

        User savedUser = userRepository.save(user);

        // Act
        userService.addFavoritePizza(savedUser.getId(), pizzaId);

        // Assert
        User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        Hibernate.initialize(updatedUser.getFavoritePizzas());
        assertTrue(updatedUser.getFavoritePizzas().contains(pizzaId));
    }

    @Test
    @Transactional
    void shouldRemoveFavoritePizza_Success() {
        // Arrange
        Long userId = 1L;
        Long pizzaId = 20L;

        List<Long> favoritePizzas = new ArrayList<>(Arrays.asList(10L, 20L, 30L));
        User user = User.builder()
                .name("John")
                .favoritePizzas(favoritePizzas)
                .build();

        // Guardamos el usuario
        User savedUser = userRepository.save(user);

        // Act
        userService.removeFavoritePizza(savedUser.getId(), pizzaId);

        // Assert
        User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        Hibernate.initialize(updatedUser.getFavoritePizzas());
        assertFalse(updatedUser.getFavoritePizzas().contains(pizzaId));
    }
}