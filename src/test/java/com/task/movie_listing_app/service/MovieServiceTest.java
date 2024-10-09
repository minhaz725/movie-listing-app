package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieServiceTest {

    private MovieService movieService;
    @BeforeEach
    void setUp() {
        movieService = new MovieServiceImpl();

        Movie movie1 = new Movie("Oppenheimer", "Cillian Murphy", "Biography, Drama, History", LocalDate.of(2023, 7, 21), 100000000);
        Movie movie2 = new Movie("Exhuma", "Choi Min-sik", "Horror, Thriller, Drama", LocalDate.of(2024, 2, 22), 15000000);
        Movie movie3 = new Movie("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
        Movie movie4 = new Movie("X-Men: Days of Future Past", "Hugh Jackman", "Action, Adventure, Sci-Fi", LocalDate.of(2014, 5, 23), 200000000);

        movieService.addMovie(movie1);
        movieService.addMovie(movie2);
        movieService.addMovie(movie3);
        movieService.addMovie(movie4);
    }

    @Test
    void testSearchMoviesByTitleCastOrCategory() {

        List<Movie> resultByTitle = movieService.searchMovies("Oppenheimer");
        assertEquals(1, resultByTitle.size());
        assertEquals("Oppenheimer", resultByTitle.get(0).getTitle());

        List<Movie> resultByCast = movieService.searchMovies("Hugh Jackman");
        assertEquals(2, resultByCast.size());
        assertEquals("Deadpool 3", resultByCast.get(0).getTitle());
        assertEquals("X-Men: Days of Future Past", resultByCast.get(1).getTitle());

        List<Movie> resultByCategory = movieService.searchMovies("Drama");
        assertEquals(2, resultByCategory.size());
        assertEquals("Exhuma", resultByCategory.get(0).getTitle());
        assertEquals("Oppenheimer", resultByCategory.get(1).getTitle());
    }

    @Test
    void testGetMovieDetails() {

        Movie movieDetails = movieService.getMovieDetails("Oppenheimer");
        assertNotNull(movieDetails);
        assertEquals("Oppenheimer", movieDetails.getTitle());
        assertEquals("Cillian Murphy", movieDetails.getCast());
        assertEquals("Biography, Drama, History", movieDetails.getCategory());

        Movie nonExistentMovie = movieService.getMovieDetails("Nonexistent Movie");
        assertNull(nonExistentMovie);
    }
}
