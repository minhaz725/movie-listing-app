package com.task.movie_listing_app.controller;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return ResponseEntity.ok("Movie added successfully.");
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<Movie>> searchMovies(@PathVariable String searchTerm) {
        List<Movie> movies = movieService.searchMovies(searchTerm);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{title}/details")
    public ResponseEntity<Movie> getMovieDetails(@PathVariable String title) {
        Movie movie = movieService.getMovieDetails(title);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(movie);
    }
}
