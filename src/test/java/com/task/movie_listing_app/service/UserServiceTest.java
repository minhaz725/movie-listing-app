package com.task.movie_listing_app.service;

import com.task.movie_listing_app.model.Movie;
import com.task.movie_listing_app.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    public static final String testUserEmail = "alice@gmail.com";

    @Test
    void testRegisterUser() {
        UserService userService = new UserServiceImpl();

        User user = userService.registerUser(testUserEmail);
        assertNotNull(user);
        assertEquals(testUserEmail, user.getEmail());

        User sameUser = userService.registerUser(testUserEmail);
        assertEquals(user, sameUser);
    }

    @Test
    void testAddRemoveFavorites() {
        UserService userService = new UserServiceImpl();
        User user = userService.registerUser(testUserEmail);

        Movie movie = new Movie("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
        userService.addToFavorites(user, movie);

        assertEquals(1, user.getFavorites().size());
        assertEquals("Deadpool 3", user.getFavorites().get(0).getTitle());

        userService.removeFromFavorites(user, movie);
        assertEquals(0, user.getFavorites().size());
    }

    @Test
    void testAddSameMovieTwice() {
        UserService userService = new UserServiceImpl();
        User user = userService.registerUser(testUserEmail);

        Movie movie = new Movie("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
        userService.addToFavorites(user, movie);
        userService.addToFavorites(user, movie); // adding the same movie again

        assertEquals(1, user.getFavorites().size()); // size should still be 1
        assertEquals("Deadpool 3", user.getFavorites().get(0).getTitle());
    }

    @Test
    void testRemoveNonExistentMovie() {
        UserService userService = new UserServiceImpl();
        User user = userService.registerUser(testUserEmail);

        Movie movie = new Movie("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
        Movie anotherMovie = new Movie("X-Men: Days of Future Past", "James McAvoy", "Action, Sci-Fi", LocalDate.of(2014, 5, 23), 200000000);

        userService.addToFavorites(user, movie);
        userService.removeFromFavorites(user, anotherMovie); // removing a movie that wasn't added

        assertEquals(1, user.getFavorites().size()); // size should still be 1
        assertEquals("Deadpool 3", user.getFavorites().get(0).getTitle());
    }

    @Test
    void testGetUserFavorites() {
        UserService userService = new UserServiceImpl();
        User user = userService.registerUser(testUserEmail);

        Movie movie1 = new Movie("Deadpool 3", "Hugh Jackman", "Action, Comedy, Sci-Fi", LocalDate.of(2024, 5, 3), 150000000);
        Movie movie2 = new Movie("X-Men: Days of Future Past", "James McAvoy", "Action, Sci-Fi", LocalDate.of(2014, 5, 23), 200000000);
        Movie movie3 = new Movie("Oppenheimer", "Cillian Murphy", "Drama, History", LocalDate.of(2023, 7, 21), 100000000);

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

}
