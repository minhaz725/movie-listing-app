package com.task.movie_listing_app.controller;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.payload.req.MovieCreationRequest;
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
    public ResponseEntity<String> addMovie(@RequestBody MovieCreationRequest movie) {
        String msg = movieService.addMovie(movie);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<MovieModel>> searchMovies(@PathVariable String searchTerm) {
        List<MovieModel> movies = movieService.searchMovies(searchTerm);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{title}/details")
    public ResponseEntity<MovieModel> getMovieDetails(@PathVariable String title) {
        MovieModel movie = movieService.getMovieDetails(title);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(movie);
    }

    @GetMapping("")
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        List<MovieModel> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }
}
