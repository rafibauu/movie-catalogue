package com.example.moviecatalogue.interfaces;

import com.example.moviecatalogue.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {

    @GET("3/movie/{kind}")
    Call<MovieResponse> getMovieList(@Path("kind") String kind, @Query("api_key") String apiKey);

    @GET("3/movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") String id, @Query("api_key") String apiKey);

}
