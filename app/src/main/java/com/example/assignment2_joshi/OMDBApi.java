package com.example.assignment2_joshi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDBApi {

    // For searching movies by title
    @GET("/")
    Call<MovieResponse> searchMovies(
            @Query("s") String searchQuery,
            @Query("apikey") String apiKey
    );

    // For getting full movie details by IMDb ID
    @GET("/")
    Call<MovieDetails> getMovieDetails(
            @Query("i") String imdbID,
            @Query("apikey") String apiKey
    );
}
