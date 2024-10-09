package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.model.User;

import java.util.List;

public interface UserService {
    User registerUser(String email);
    User getUserByEmail(String email);
    void addToFavorites(String email, Movie movie);
    void removeFromFavorites(String email, Movie movie);
    List<Movie> getUserFavorites(String email);
    List<Movie> searchFavoriteMovies(String email, String searchTerm);
}
