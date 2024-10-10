package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    UserModel registerUser(String email);
    UserModel getUserByEmail(String email);
    void addToFavorites(String email, MovieModel movie);
    void removeFromFavorites(String email, MovieModel movie);
    List<MovieModel> getUserFavorites(String email);
    List<MovieModel> searchFavoriteMovies(String email, String searchTerm);
}
