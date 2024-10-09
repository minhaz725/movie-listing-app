package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;

import java.util.List;

public interface MovieService {
    void addMovie(Movie movie);
    List<Movie> searchMovies(String searchTerm);
    Movie getMovieDetails(String title);
    ///todo: assuming movie details query will be asked by title only for now, can be improved
}
