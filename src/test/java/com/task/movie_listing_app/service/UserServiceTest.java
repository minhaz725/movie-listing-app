package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    public static final String testUserEmail1 = "alice@gmail.com";
    public static final String testUserEmail2 = "bob@gmail.com";
    public static final Movie movie1 = new Movie("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
    public static final Movie movie2 = new Movie("X-Men: Days of Future Past", "Hugh Jackman", "Action, Sci-Fi", LocalDate.of(2014, 5, 23), 200000000);
    public static final Movie movie3 = new Movie("Oppenheimer", "Cillian Murphy", "Drama, History", LocalDate.of(2023, 7, 21), 100000000);

    public static final Movie movie4 = new Movie("Exhuma", "Choi Min-sik", "Horror, Thriller, Drama", LocalDate.of(2024, 2, 22), 15000000);

    private UserService userService;
    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }
    @Test
    void testRegisterUser() {

        User user = userService.registerUser(testUserEmail1);
        assertNotNull(user);
        assertEquals(testUserEmail1, user.getEmail());

        User sameUser = userService.registerUser(testUserEmail1);
        assertEquals(user, sameUser);
    }

    @Test
    void testRegisterMultipleUsers() {
        User user1 = userService.registerUser(testUserEmail1);
        User user2 = userService.registerUser(testUserEmail2);

        assertNotNull(user1);
        assertNotNull(user2);
        assertNotEquals(user1, user2);
    }

    @Test
    void testAddRemoveFavorites() {

        User user = userService.registerUser(testUserEmail1);

        userService.addToFavorites(user, movie1);

        assertEquals(1, user.getFavorites().size());
        assertEquals("Deadpool 3", user.getFavorites().get(0).getTitle());

        userService.removeFromFavorites(user, movie1);
        assertEquals(0, user.getFavorites().size());
    }

    @Test
    void testAddSameMovieTwice() {

        User user = userService.registerUser(testUserEmail1);

        userService.addToFavorites(user, movie1);
        userService.addToFavorites(user, movie1); // adding the same movie again

        assertEquals(1, user.getFavorites().size()); // size should still be 1
        assertEquals("Deadpool 3", user.getFavorites().get(0).getTitle());
    }

    @Test
    void testRemoveNonExistentMovie() {

        User user = userService.registerUser(testUserEmail1);

        userService.addToFavorites(user, movie1);
        userService.removeFromFavorites(user, movie2); // removing a movie that wasn't added

        assertEquals(1, user.getFavorites().size()); // size should still be 1
        assertEquals("Deadpool 3", user.getFavorites().get(0).getTitle());
    }

    @Test
    void testGetUserFavorites() {

        User user = userService.registerUser(testUserEmail1);

        // add movies in unsorted order
        userService.addToFavorites(user, movie1);
        userService.addToFavorites(user, movie3);
        userService.addToFavorites(user, movie2);

        // retrieve favorites
        List<Movie> favorites = userService.getUserFavorites(user);

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

        User foundUser = userService.getUserByEmail(testUserEmail1);
        assertNotNull(foundUser);

        assertEquals(testUserEmail1, foundUser.getEmail());

        // test with an email that doesn't exist
        User notFoundUser = userService.getUserByEmail(testUserEmail2);
        assertNull(notFoundUser);
    }

    @Test
    void testSearchFavoriteMoviesByTitleCastOrCategory() {

        User user = userService.registerUser(testUserEmail1);

        userService.addToFavorites(user, movie1);
        userService.addToFavorites(user, movie2);
        userService.addToFavorites(user, movie3);
        userService.addToFavorites(user, movie4);

        List<Movie> resultByTitle = userService.searchFavoriteMovies(user, "Oppenheimer");

        assertEquals(1, resultByTitle.size());
        assertEquals("Oppenheimer", resultByTitle.get(0).getTitle());

        List<Movie> resultByCast = userService.searchFavoriteMovies(user,"Hugh Jackman");
        assertEquals(2, resultByCast.size());
        assertEquals("Deadpool 3", resultByCast.get(0).getTitle());
        assertEquals("X-Men: Days of Future Past", resultByCast.get(1).getTitle());

        List<Movie> resultByCategory = userService.searchFavoriteMovies(user,"Drama");
        assertEquals(2, resultByCategory.size());
        assertEquals("Exhuma", resultByCategory.get(0).getTitle());
        assertEquals("Oppenheimer", resultByCategory.get(1).getTitle());
    }

}
