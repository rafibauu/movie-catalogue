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

    @GET("3/search/tv")
    Call<TvResponse> searchTv(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("3/discover/tv")
    Call<TvResponse> newRelease(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String date1,
            @Query("primary_release_date.lte") String date2);

}
