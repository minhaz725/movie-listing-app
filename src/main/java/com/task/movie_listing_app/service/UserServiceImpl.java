package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.model.User;
import com.task.movie_listing_app.utils.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserServiceImpl implements UserService {
    private final List<User> users = new ArrayList<>();

    @Override
    public User registerUser(String email) {

        User existingUser = getUserByEmail(email);
        if (existingUser != null) {
            log.info("User is already registered.");
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
        /// todo: here instead of handling error, I'll simply sending null for simplicity for now
    }
    @Override
    public void addToFavorites(User user, Movie movie) {
        if (!user.getFavorites().contains(movie)) {
            user.getFavorites().add(movie);
        } else {
            log.info("Movie is already in the favorites list.");
        }
    }
    @Override
    public void removeFromFavorites(User user, Movie movie) {
        user.getFavorites().removeIf(favMovie -> favMovie.equals(movie));
    }

    @Override
    public List<Movie> getUserFavorites(User user) {
        return user.getFavorites().stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> searchFavoriteMovies(User user, String searchTerm) {
        return Util.filterMovies(user.getFavorites(), searchTerm);
    }
}
