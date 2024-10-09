package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final List<User> users = new ArrayList<>();

    @Override
    public User registerUser(String email) {

        User existingUser = getUserByEmail(email);
        if (existingUser != null) {
            return existingUser;
            /// todo: here instead of handling error, I'll simply sending the existing user for simplicity for now
        }

        User newUser = User.builder()
                .email(email)
                .favorites(new ArrayList<>())
                .build();
        users.add(newUser);
        return newUser;
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
