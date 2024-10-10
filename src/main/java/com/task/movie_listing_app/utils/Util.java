package com.task.movie_listing_app.utils;

import com.task.movie_listing_app.model.MovieModel;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    /// todo: for simplicity assuming single cast for now but keeping comma separated multiple categories
    public static List<MovieModel> filterMovies(List<MovieModel> movieList, String searchTerm) {
        return movieList.stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(searchTerm) ||
                        movie.getCast().equalsIgnoreCase(searchTerm) ||
                        Arrays.stream(movie.getCategory().split(", "))
                                .anyMatch(category -> category.equalsIgnoreCase(searchTerm)))
                .sorted(Comparator.comparing(MovieModel::getTitle))
                .collect(Collectors.toList());
    }

}
