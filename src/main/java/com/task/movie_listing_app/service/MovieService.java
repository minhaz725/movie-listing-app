package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;

import java.util.List;

public interface MovieService {
    void addMovie(Movie movie);
    List<Movie> searchMovies(String searchTerm);
}
