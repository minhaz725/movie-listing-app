package com.task.movie_listing_app.controller;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.model.User;
import com.task.movie_listing_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody String email) {
        User user = userService.registerUser(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{email}/favorites")
    public ResponseEntity<String> addMovieToFavorites(@PathVariable String email, @RequestBody Movie movie) {
        userService.addToFavorites(email, movie);
        return ResponseEntity.ok("Movie added to favorites.");
    }

    @DeleteMapping("/{email}/favorites")
    public ResponseEntity<String> removeMovieFromFavorites(@PathVariable String email, @RequestBody Movie movie) {
        userService.removeFromFavorites(email, movie);
        return ResponseEntity.ok("Movie removed from favorites.");
    }

    @GetMapping("/{email}/favorites")
    public ResponseEntity<List<Movie>> getUserFavorites(@PathVariable String email) {
        List<Movie> favoriteMovies = userService.getUserFavorites(email);
        return ResponseEntity.ok(favoriteMovies);
    }

    @GetMapping("/{email}/favorites/search")
    public ResponseEntity<List<Movie>> searchFavoriteMovies(@PathVariable String email, @RequestParam String searchTerm) {
        List<Movie> favoriteMovies = userService.searchFavoriteMovies(email, searchTerm);
        return ResponseEntity.ok(favoriteMovies);
    }

    @GetMapping("/{email}/details")
    public ResponseEntity<User> getUserDetails(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }
}
