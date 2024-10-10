package com.task.movie_listing_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Movie Listing App APIs",
                description = "API documentation for Movie Listing Application, allowing users to search, add, and manage favorite movies.\n",
                version = "1.0"
        )
)
@Configuration
public class OpenApiConfig {
}
