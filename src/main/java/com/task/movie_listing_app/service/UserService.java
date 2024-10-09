package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.User;

public interface UserService {
    User registerUser(String email);
    User getUserByEmail(String email);
}
