package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {
    private final List<Movie> movies = new ArrayList<>();
    @Override
    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    @Override
    public List<Movie> searchMovies(String searchTerm) {
        return movies.stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(searchTerm) ||
                        movie.getCast().equalsIgnoreCase(searchTerm) ||
                        Arrays.stream(movie.getCategory().split(", "))
                                .anyMatch(category -> category.equalsIgnoreCase(searchTerm)))
                .sorted((m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()))
                .collect(Collectors.toList());
    }

}
