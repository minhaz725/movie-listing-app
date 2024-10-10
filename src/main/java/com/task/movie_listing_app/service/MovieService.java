package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.payload.req.MovieCreationRequest;

import java.util.List;

public interface MovieService {
    void addMovie(MovieCreationRequest request);
    List<MovieModel> searchMovies(String searchTerm);
    MovieModel getMovieDetails(String title);
    ///todo: assuming movie details query will be asked by title only for now, can be improved
}
