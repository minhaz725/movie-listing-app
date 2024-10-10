package com.task.movie_listing_app.controller;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void testAddMovie() throws Exception {
        mockMvc.perform(post("/movies/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Oppenheimer\", \"actor\": \"Cillian Murphy\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie added successfully."));
    }

    @Test
    public void testSearchMovies() throws Exception {
        List<MovieModel> movies = new ArrayList<>();
        movies.add(new MovieModel("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000));
        when(movieService.searchMovies("Deadpool")).thenReturn(movies);

        mockMvc.perform(get("/movies/search/Deadpool"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Deadpool 3"));
    }

    @Test
    public void testGetMovieDetails() throws Exception {
        MovieModel movie = new MovieModel("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
        when(movieService.getMovieDetails("Deadpool 3")).thenReturn(movie);

        mockMvc.perform(get("/movies/Deadpool 3/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Deadpool 3"));
    }
}
