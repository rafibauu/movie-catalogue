package com.example.moviecatalogue.interfaces;

import com.example.moviecatalogue.models.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvInterface {

    @GET("3/tv/{kind}")
    Call<TvResponse> getTvList(@Path("kind") String kind, @Query("api_key") String apiKey);

    @GET("3/tv/{id}")
    Call<TvResponse> getTvDetails(@Path("id") String kind, @Query("api_key") String apiKey);

}
