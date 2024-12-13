package com.hiberus.services;

import com.hiberus.models.User;

import java.util.List;

public interface UserServices {
    List<User> getUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
