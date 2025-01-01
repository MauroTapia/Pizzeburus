package com.hiberus.controllers;


import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;
import com.hiberus.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServices.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get user by ID",
                description = ".")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userServices.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        User createdUser = userServices.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Actualizar un usuario existente")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado")
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody CreateUserDto updateUserDto) {
        User updatedUser = userServices.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Eliminar usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        User user = userServices.getUserById(id);
        if (user != null) {
            userServices.deleteUser(id);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Agregar pizza a favoritos del usuario", description = "Agrega una pizza a los favoritos del usuario especificado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pizza agregada como favorita"),
            @ApiResponse(responseCode = "404", description = "Usuario o pizza no encontrados"),
            @ApiResponse(responseCode = "400", description = "La pizza ya está en favoritos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/{userId}/favorites/{pizzaId}")
    public ResponseEntity<String> addFavoritePizza(@PathVariable Long userId, @PathVariable Long pizzaId) {
        userServices.addFavoritePizza(userId, pizzaId);
        return ResponseEntity.ok("Pizza marcada como favorita");
    }

    @Operation(summary = "Eliminar pizza de favoritos del usuario", description = "Elimina una pizza de los favoritos del usuario especificado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pizza eliminada de favoritos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "La pizza no estaba en favoritos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{userId}/favorites/{pizzaId}")
    public ResponseEntity<String> removeFavoritePizza(@PathVariable Long userId, @PathVariable Long pizzaId) {
        userServices.removeFavoritePizza(userId, pizzaId);
        return ResponseEntity.ok("Pizza desmarcada como favorita");
    }

}