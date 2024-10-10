package com.task.movie_listing_app.controller;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.model.UserModel;
import com.task.movie_listing_app.payload.req.MovieAddOrRemoveToFavoriteRequest;
import com.task.movie_listing_app.payload.req.UserCreationRequest;
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
    public ResponseEntity<UserModel> registerUser(@RequestBody UserCreationRequest userCreationRequest) {
        UserModel user = userService.registerUser(userCreationRequest.getEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{email}/favorites")
    public ResponseEntity<String> addMovieToFavorites(@PathVariable String email, @RequestBody MovieAddOrRemoveToFavoriteRequest request) {
        userService.addToFavorites(email, request.getTitle());
        return ResponseEntity.ok("Movie added to favorites.");
    }

    @DeleteMapping("/{email}/favorites")
    public ResponseEntity<String> removeMovieFromFavorites(@PathVariable String email, @RequestBody MovieAddOrRemoveToFavoriteRequest request) {
        userService.removeFromFavorites(email, request.getTitle());
        return ResponseEntity.ok("Movie removed from favorites.");
    }

    @GetMapping("/{email}/favorites")
    public ResponseEntity<List<MovieModel>> getUserFavorites(@PathVariable String email) {
        List<MovieModel> favoriteMovies = userService.getUserFavorites(email);
        return ResponseEntity.ok(favoriteMovies);
    }

    @GetMapping("/{email}/favorites/search")
    public ResponseEntity<List<MovieModel>> searchFavoriteMovies(@PathVariable String email, @RequestParam String searchTerm) {
        List<MovieModel> favoriteMovies = userService.searchFavoriteMovies(email, searchTerm);
        return ResponseEntity.ok(favoriteMovies);
    }

    @GetMapping("/{email}/details")
    public ResponseEntity<UserModel> getUserDetails(@PathVariable String email) {
        UserModel user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }
}
