package com.task.movie_listing_app.payload.req;

import com.task.movie_listing_app.model.MovieModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationRequest {
    private String email;
    private List<MovieModel> favorites;
}