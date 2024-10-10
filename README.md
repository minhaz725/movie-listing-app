# Movie Listing App

This is a basic movie listing application similar to IMDb, 
allowing users to register, search for movies, and manage their favorite movies. 
Users can add or remove movies from their favorites list and view their favorite movies.
Data is kept in an in-memory array list, some minimal log is used and currently no proper
error handling is included.

## Features
- User registration
- Add and remove movies from favorites
- Search for movies by title
- View details of movies
- View user favorites

## Technologies Used
- Java 17
- Spring Boot 3
- Gradle 8
- Swagger for API documentation

## Prerequisites
To run this project, you need:
- Java 17
- Gradle (included via Gradle wrapper)
- Git

## Steps to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/minhaz725/movie-listing-app.git
   
2. Navigate to the project directory:
    ```bash
    cd movie-listing-app

3. Clean build using Gradle wrapper:
   ```bash
    ./gradlew clean build

4. Run the tests:
    ```bash
    ./gradlew test   
   
5. Run the application:
    ```bash
    ./gradlew bootRun   
   
The application should now be running on http://localhost:10000/movie-listing-app/api/v1

## Pre Populate
As the project got volatile data, added a property movies.populate in app properties, if it's set true,
then you will get some sample movie data already populated.

## API Documentation
For detailed API documentation, please visit the [Swagger API Documentation](http://localhost:10000/movie-listing-app/api/v1/swagger-ui/index.html#/).
