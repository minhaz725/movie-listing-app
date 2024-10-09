package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final List<Movie> movies = new ArrayList<>();
    @Override
    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    @Override
    public List<Movie> searchMovies(String searchTerm) {
        return Util.filterMovies(movies, searchTerm);
    }
    @Override
    public Movie getMovieDetails(String title) {
        return movies.stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
        /// todo: here instead of handling error, I'll simply sending null for simplicity for now
    }

}
