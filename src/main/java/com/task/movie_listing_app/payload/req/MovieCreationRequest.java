package com.task.movie_listing_app.payload.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieCreationRequest {
    private String title;
    private String cast;
    private String category;
    private LocalDate releaseDate;
    private double budget;
}