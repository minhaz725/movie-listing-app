package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.payload.req.MovieCreationRequest;
import com.task.movie_listing_app.utils.Util;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    private final List<MovieModel> movies = new ArrayList<>();

    @Value("${movies.populate:false}")
    private boolean populateMovies;
    @PostConstruct
    public void init() {
        if (populateMovies) {
            populateMovies();
        }
    }
    private void populateMovies() {

        log.info("Populating movie list with some movies.");

        MovieModel movie1 = new MovieModel("Oppenheimer", "Cillian Murphy", "Biography, Drama, History", LocalDate.of(2023, 7, 21), 100000000);
        MovieModel movie2 = new MovieModel("Exhuma", "Choi Min-sik", "Horror, Thriller, Drama", LocalDate.of(2024, 2, 22), 15000000);
        MovieModel movie3 = new MovieModel("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
        MovieModel movie4 = new MovieModel("X-Men: Days of Future Past", "Hugh Jackman", "Action, Adventure, Sci-Fi", LocalDate.of(2014, 5, 23), 200000000);

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
    }
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

    @Override
    public List<MovieModel> getAllMovies() {
        return this.movies;
    }

}
