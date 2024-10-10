package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.payload.req.MovieCreationRequest;
import com.task.movie_listing_app.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    private final List<MovieModel> movies = new ArrayList<>();
    @Override
    public void addMovie(MovieCreationRequest request) {
        MovieModel movieModel = getMovieDetails(request.getTitle());
        if(movieModel != null) {
            // assuming every movie has unique title
            log.info("Movie is already present in the movies list.");
            return;
        }
        movies.add(Util.convertMovieCreationRequestToMovieModel(request));
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
