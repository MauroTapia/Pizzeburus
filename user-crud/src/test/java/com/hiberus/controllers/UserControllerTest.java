package com.hiberus.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hiberus.models.Pizza;
import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;
import com.hiberus.services.UserServices;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserServices userServices;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers_ReturnsUserList() throws Exception {
        List<User> mockUsers = List.of(new User(1L, "John", List.of(1L)), new User(2L, "Jane", List.of(2L)));

        when(userServices.getUsers()).thenReturn(mockUsers);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"));

        verify(userServices, times(1)).getUsers();
    }

    @Test
    void testGetAllUsers_NoUsersFound() throws Exception {
        when(userServices.getUsers()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isNotFound());

        verify(userServices, times(1)).getUsers();
    }

    @Test
    void testGetAllUsers_InternalServerError() throws Exception {
        when(userServices.getUsers()).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isInternalServerError());

        verify(userServices, times(1)).getUsers();
    }

    @Test
    void testGetUserById_UserFound() throws Exception {
        User mockUser = new User(1L, "John", List.of(1L));

        when(userServices.getUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));

        verify(userServices, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() throws Exception {
        when(userServices.getUserById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());

        verify(userServices, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser_UserCreated() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("John");
        User mockUser = new User(1L, "John", List.of(1L));

        when(userServices.createUser(any(CreateUserDto.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"pizzaIds\":[1]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));

        verify(userServices, times(1)).createUser(any(CreateUserDto.class));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        Long userId = 1L;

        CreateUserDto updateUserDto = new CreateUserDto("Updated Name");

        User updatedUser = User.builder()
                .id(userId)
                .name("Updated Name")
                .favoritePizzas(List.of(1L, 2L)) // Favoritas preexistentes
                .build();

        when(userServices.updateUser(eq(userId), any(CreateUserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(patch("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.favoritePizzas[0]").value(1))
                .andExpect(jsonPath("$.favoritePizzas[1]").value(2));

        // Verificación
        verify(userServices, times(1)).updateUser(eq(userId), any(CreateUserDto.class));
    }

    @Test
    void testDeleteUserById_UserExists() throws Exception {
        Long userId = 1L;
        User mockUser = User.builder().id(userId).name("John").favoritePizzas(List.of(1L, 2L)).build();

        // Simulamos que el usuario existe en el servicio
        when(userServices.getUserById(userId)).thenReturn(mockUser);

        // Simulamos que el servicio elimina al usuario
        doNothing().when(userServices).deleteUser(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

        // Verificamos que el servicio fue llamado para obtener al usuario y eliminarlo
        verify(userServices, times(1)).getUserById(userId);
        verify(userServices, times(1)).deleteUser(userId);
    }
    @Test
    void testDeleteUserById_UserNotFound() throws Exception {
        Long userId = 1L;

        // Simulamos que el usuario no existe
        when(userServices.getUserById(userId)).thenReturn(null);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));

        // Verificamos que el servicio fue llamado para obtener al usuario
        verify(userServices, times(1)).getUserById(userId);
    }

    @Test
    void testAddFavoritePizza_Success() throws Exception {
        Long userId = 1L;
        Long pizzaId = 1L;

        User mockUser = User.builder()
                .id(userId)
                .name("John")
                .favoritePizzas(new ArrayList<>())
                .build();

        Pizza mockPizza = Pizza.builder()
                .id(pizzaId)
                .name("Margherita")
                .build();

        when(userServices.getUserById(userId)).thenReturn(mockUser);
        when(userServices.addFavoritePizza(userId, pizzaId)).thenReturn(mockPizza);

        mockMvc.perform(post("/api/users/{userId}/favorites/{pizzaId}", userId, pizzaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pizzaId))
                .andExpect(jsonPath("$.name").value("Margherita"));

        verify(userServices, times(1)).addFavoritePizza(userId, pizzaId);
    }


    @Test
    void testAddFavoritePizza_UserNotFound() throws Exception {
        Long userId = 1L;
        Long pizzaId = 1L;

        // Simulamos que el servicio lanza una excepción cuando el usuario no existe
        when(userServices.addFavoritePizza(userId, pizzaId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        mockMvc.perform(post("/api/users/{userId}/favorites/{pizzaId}", userId, pizzaId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));

        // Verificamos que el servicio fue llamado
        verify(userServices, times(1)).addFavoritePizza(userId, pizzaId);
    }
    @Test
    void testRemoveFavoritePizza_Success() throws Exception {
        Long userId = 1L;
        Long pizzaId = 1L;

        // Simulamos que el usuario existe y tiene pizzas favoritas
        User mockUser = User.builder().id(userId).name("John").favoritePizzas(List.of(pizzaId)).build();
        when(userServices.getUserById(userId)).thenReturn(mockUser);

        // Simulamos que la pizza se elimina correctamente
        doNothing().when(userServices).removeFavoritePizza(userId, pizzaId);

        mockMvc.perform(delete("/api/users/{userId}/favorites/{pizzaId}", userId, pizzaId))
                .andExpect(status().isOk())
                .andExpect(content().string("Pizza desmarcada como favorita"));

        // Verificamos que el servicio fue llamado para eliminar la pizza de favoritos
        verify(userServices, times(1)).removeFavoritePizza(userId, pizzaId);
    }

}

