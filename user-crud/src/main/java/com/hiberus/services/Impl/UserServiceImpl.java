package com.hiberus.services.Impl;


import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class UserServiceImpl implements UserServices {

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
            existingUser.setPizzafav(userDetails.getPizzafav());

            return userRepository.save(existingUser);
        }

        @Override
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }
    }
