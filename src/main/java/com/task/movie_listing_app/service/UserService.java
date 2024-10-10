package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.model.UserModel;
import com.task.movie_listing_app.payload.req.MovieAddOrRemoveToFavoriteRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    UserModel registerUser(String email);
    UserModel getUserByEmail(String email);

    // adding or removing movie with only title for simplicity
    String addToFavorites(String email, String title);
    String removeFromFavorites(String email, String title);
    List<MovieModel> getUserFavorites(String email);
    List<MovieModel> searchFavoriteMovies(String email, String searchTerm);
}
