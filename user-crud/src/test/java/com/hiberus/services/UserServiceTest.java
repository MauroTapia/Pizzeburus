package com.hiberus.services;

import com.hiberus.feignConfig.FeignPizzasRead;
import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}