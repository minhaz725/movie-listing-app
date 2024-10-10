package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.MovieModel;
import com.task.movie_listing_app.model.UserModel;
import com.task.movie_listing_app.payload.req.MovieCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    public static final String testUserEmail1 = "alice@gmail.com";
    public static final String testUserEmail2 = "bob@gmail.com";
    public static final MovieCreationRequest movie1 = new MovieCreationRequest("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
    public static final MovieCreationRequest movie2 = new MovieCreationRequest("X-Men: Days of Future Past", "Hugh Jackman", "Action, Sci-Fi", LocalDate.of(2014, 5, 23), 200000000);
    public static final MovieCreationRequest movie3 = new MovieCreationRequest("Oppenheimer", "Cillian Murphy", "Drama, History", LocalDate.of(2023, 7, 21), 100000000);
    public static final MovieCreationRequest movie4 = new MovieCreationRequest("Exhuma", "Choi Min-sik", "Horror, Thriller, Drama", LocalDate.of(2024, 2, 22), 15000000);

    @Mock
    private MovieService movieService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserModel user = userService.registerUser(testUserEmail1);
        assertNotNull(user);
        assertEquals(testUserEmail1, user.getEmail());

        UserModel sameUser = userService.registerUser(testUserEmail1);
        assertEquals(user, sameUser);
    }

    @Test
    void testRegisterMultipleUsers() {
        UserModel user1 = userService.registerUser(testUserEmail1);
        UserModel user2 = userService.registerUser(testUserEmail2);

        assertNotNull(user1);
        assertNotNull(user2);
        assertNotEquals(user1, user2);
    }

    @Test
    void testAddRemoveFavorites() {
        registerUserAndMockMovies(testUserEmail1, movie1);

        String addResult = userService.addToFavorites(testUserEmail1, movie1.getTitle());
        assertEquals("Movie added to favorites.", addResult);

        List<MovieModel> favorites = userService.getUserFavorites(testUserEmail1);
        assertEquals(1, favorites.size());
        assertEquals(movie1.getTitle(), favorites.get(0).getTitle());

        String removeResult = userService.removeFromFavorites(testUserEmail1, movie1.getTitle());
        assertEquals("Movie removed from favorites.", removeResult);

        favorites = userService.getUserFavorites(testUserEmail1);
        assertEquals(0, favorites.size());
    }

    @Test
    void testAddSameMovieTwice() {
        registerUserAndMockMovies(testUserEmail1, movie1);

        String addResult = userService.addToFavorites(testUserEmail1, movie1.getTitle());
        assertEquals("Movie added to favorites.", addResult);

        String addResultAgain = userService.addToFavorites(testUserEmail1, movie1.getTitle());
        assertEquals("Movie is already in the favorites list.", addResultAgain);

        List<MovieModel> favorites = userService.getUserFavorites(testUserEmail1);
        assertEquals(1, favorites.size()); // Size should still be 1
        assertEquals(movie1.getTitle(), favorites.get(0).getTitle());
    }

    @Test
    void testRemoveNonExistentMovie() {
        registerUserAndMockMovies(testUserEmail1, movie1);

        String addResult = userService.addToFavorites(testUserEmail1, movie1.getTitle());
        assertEquals("Movie added to favorites.", addResult);

        String removeResult = userService.removeFromFavorites(testUserEmail1, movie2.getTitle());
        assertEquals("Movie doesn't exist in Users favorite list.", removeResult);

        List<MovieModel> favorites = userService.getUserFavorites(testUserEmail1);
        assertEquals(1, favorites.size()); // Size should still be 1
        assertEquals(movie1.getTitle(), favorites.get(0).getTitle());
    }


    @Test
    void testGetUserFavorites() {
        registerUserAndMockMovies(testUserEmail1, movie1, movie2, movie3);

        // add movies in unsorted order
        userService.addToFavorites(testUserEmail1, movie1.getTitle());
        userService.addToFavorites(testUserEmail1, movie3.getTitle());
        userService.addToFavorites(testUserEmail1, movie2.getTitle());

        // retrieve favorites
        List<MovieModel> favorites = userService.getUserFavorites(testUserEmail1);

        // verify size
        assertEquals(3, favorites.size());

        // verify the order is sorted by title
        assertEquals("Deadpool 3", favorites.get(0).getTitle());
        assertEquals("Oppenheimer", favorites.get(1).getTitle());
        assertEquals("X-Men: Days of Future Past", favorites.get(2).getTitle());
    }

    @Test
    void testGetUserByEmail() {
        userService.registerUser(testUserEmail1);

        UserModel foundUser = userService.getUserByEmail(testUserEmail1);
        assertNotNull(foundUser);

        assertEquals(testUserEmail1, foundUser.getEmail());

        // test with an email that doesn't exist
        UserModel notFoundUser = userService.getUserByEmail(testUserEmail2);
        assertNull(notFoundUser);
    }

    @Test
    void testSearchFavoriteMoviesByTitleCastOrCategory() {
        registerUserAndMockMovies(testUserEmail1, movie1, movie2, movie3, movie4);

        userService.addToFavorites(testUserEmail1, movie1.getTitle());
        userService.addToFavorites(testUserEmail1, movie2.getTitle());
        userService.addToFavorites(testUserEmail1, movie3.getTitle());
        userService.addToFavorites(testUserEmail1, movie4.getTitle());

        List<MovieModel> resultByTitle = userService.searchFavoriteMovies(testUserEmail1, "Oppenheimer");
        assertEquals(1, resultByTitle.size());
        assertEquals("Oppenheimer", resultByTitle.get(0).getTitle());

        List<MovieModel> resultByCast = userService.searchFavoriteMovies(testUserEmail1, "Hugh Jackman");
        assertEquals(2, resultByCast.size());
        assertEquals("Deadpool 3", resultByCast.get(0).getTitle());
        assertEquals("X-Men: Days of Future Past", resultByCast.get(1).getTitle());

        List<MovieModel> resultByCategory = userService.searchFavoriteMovies(testUserEmail1, "Drama");
        assertEquals(2, resultByCategory.size());
        assertEquals("Exhuma", resultByCategory.get(0).getTitle());
        assertEquals("Oppenheimer", resultByCategory.get(1).getTitle());
    }

    // utility to create a MovieModel from MovieCreationRequest
    private MovieModel createMovieModel(MovieCreationRequest movieRequest) {
        return new MovieModel(movieRequest.getTitle(), movieRequest.getCast(), movieRequest.getCategory(), movieRequest.getReleaseDate(), movieRequest.getBudget());
    }

    // utility to register user and mock movie service
    private void registerUserAndMockMovies(String email, MovieCreationRequest... movies) {
        userService.registerUser(email);
        for (MovieCreationRequest movie : movies) {
            MovieModel movieModel = createMovieModel(movie);
            when(movieService.searchMovies(movie.getTitle())).thenReturn(List.of(movieModel));
        }
    }
}
