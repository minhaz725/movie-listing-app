package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final List<MovieModel> movies = new ArrayList<>();
    @Override
    public void addMovie(MovieModel movie) {
        movies.add(movie);
    }
    @Override
    public List<MovieModel> searchMovies(String searchTerm) {
        return Util.filterMovies(movies, searchTerm);
    }
    @Override
    public MovieModel getMovieDetails(String title) {
        return movies.stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
        /// todo: here instead of handling error, I'll simply sending null for simplicity for now
    }

}
