package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    public static final String testUserEmail = "alice@gmail.com";

    @Test
    void testRegisterUser() {
        UserService userService = new UserServiceImpl();

        User user = userService.registerUser(testUserEmail);
        assertNotNull(user);
        assertEquals(testUserEmail, user.getEmail());

        User sameUser = userService.registerUser(testUserEmail);
        assertEquals(user, sameUser);
    }
}
