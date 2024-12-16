package com.hiberus.controllers;


import com.hiberus.models.User;
import com.hiberus.models.dto.CreateUserDto;
import com.hiberus.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServices.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userServices.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        User createdUser = userServices.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody CreateUserDto updateUserDto) {
        User updatedUser = userServices.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

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

    @PostMapping("/{userId}/favorites/{pizzaId}")
    public ResponseEntity<String> addFavoritePizza(@PathVariable Long userId, @PathVariable Long pizzaId) {
        userServices.addFavoritePizza(userId, pizzaId);
        return ResponseEntity.ok("Pizza marcada como favorita");
    }

    @DeleteMapping("/{userId}/favorites/{pizzaId}")
    public ResponseEntity<String> removeFavoritePizza(@PathVariable Long userId, @PathVariable Long pizzaId) {
        userServices.removeFavoritePizza(userId, pizzaId);
        return ResponseEntity.ok("Pizza desmarcada como favorita");
    }

}