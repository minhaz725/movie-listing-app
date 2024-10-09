package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.model.User;

import java.util.List;

public interface UserService {
    User registerUser(String email);
    User getUserByEmail(String email);
    void addToFavorites(User user, Movie movie);
    void removeFromFavorites(User user, Movie movie);
    List<Movie> getUserFavorites(User user);
    List<Movie> searchFavoriteMovies(User user, String searchTerm);
}
