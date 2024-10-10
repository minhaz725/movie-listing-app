package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.model.UserModel;
import com.task.movie_listing_app.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MovieService movieService;
    private final List<UserModel> users = new ArrayList<>();

    @Override
    public UserModel registerUser(String email) {

        UserModel existingUser = getUserByEmail(email);
        if (existingUser != null) {
            log.info("User is already registered.");
            return existingUser;
            /// todo: here instead of handling error, I'll simply sending the existing user for simplicity for now
        }

        UserModel newUser = UserModel.builder()
                .email(email)
                .favorites(new ArrayList<>())
                .build();
        users.add(newUser);
        return newUser;
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
        /// todo: here instead of handling error, I'll simply sending null for simplicity for now
    }
    @Override
    public void addToFavorites(String email, String title) {
        UserModel user = getUserByEmail(email);
        // assuming every movie has unique title
        List<MovieModel> movieList = movieService.searchMovies(title);
        if (movieList.isEmpty()) {
            log.info("Movie doesn't exist in DB.");
            /// todo: here instead of handling error, I'll simply logging for simplicity for now
            return;
        }
        if (!user.getFavorites().contains(movieList.get(0))) {
            user.getFavorites().add(movieList.get(0));
        } else {
            log.info("Movie is already in the favorites list.");
        }
    }
    @Override
    public void removeFromFavorites(String email, String title) {
        UserModel user = getUserByEmail(email);
        List<MovieModel> movieList = searchFavoriteMovies(user.getEmail(), title);
        if (movieList.isEmpty()) {
            log.info("Movie doesn't exist in Users favorite list.");
            /// todo: here instead of handling error, I'll simply logging for simplicity for now
            return;
        }
        user.getFavorites().removeIf(favMovie -> favMovie.equals(movieList.get(0)));
    }

    @Override
    public List<MovieModel> getUserFavorites(String email) {
        UserModel user = getUserByEmail(email);
        return user.getFavorites().stream()
                .sorted(Comparator.comparing(MovieModel::getTitle))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieModel> searchFavoriteMovies(String email, String searchTerm) {
        UserModel user = getUserByEmail(email);
        return Util.filterMovies(user.getFavorites(), searchTerm);
    }
}
