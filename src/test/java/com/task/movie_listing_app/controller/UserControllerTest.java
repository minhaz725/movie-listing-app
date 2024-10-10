package com.task.movie_listing_app.controller;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.model.UserModel;
import com.task.movie_listing_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterUser() throws Exception {
        UserModel user = new UserModel("test@example.com", new ArrayList<>());
        when(userService.registerUser(anyString())).thenReturn(user);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testAddToFavorites() throws Exception {
        mockMvc.perform(post("/users/test@example.com/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Oppenheimer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie added to favorites."));
    }

    @Test
    void testRemoveFromFavorites() throws Exception {
        mockMvc.perform(delete("/users/test@example.com/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Oppenheimer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie removed from favorites."));
    }

    @Test
    void testGetUserFavorites() throws Exception {
        List<MovieModel> favoriteMovies = List.of(new MovieModel("Oppenheimer", "Cillian Murphy", "Drama, History", LocalDate.of(2023, 7, 21), 100000000));
        when(userService.getUserFavorites(anyString())).thenReturn(favoriteMovies);

        mockMvc.perform(get("/users/test@example.com/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Oppenheimer"));
    }

    @Test
    void testGetUserDetails_UserNotFound() throws Exception {
        when(userService.getUserByEmail("unknown@example.com")).thenReturn(null);

        mockMvc.perform(get("/users/unknown@example.com/details"))
                .andExpect(status().isNotFound());
    }
}

