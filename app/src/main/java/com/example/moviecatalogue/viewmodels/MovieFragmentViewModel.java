package com.example.moviecatalogue.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalogue.interfaces.MovieInterface;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.models.MovieResponse;
import com.example.moviecatalogue.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragmentViewModel extends ViewModel {

    private static final String API_KEY = "ea8aa5120eed2fdb1d312cf637abf995";

    private MutableLiveData<List<Movie>> allMovie;
    private MutableLiveData<List<Movie>> popularMovies;

    public LiveData<List<Movie>> getAllMovie() {
        if (allMovie == null) {
            allMovie = new MutableLiveData<List<Movie>>();
            loadAllMovie();
        }
        return allMovie;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        if (popularMovies == null) {
            popularMovies = new MutableLiveData<List<Movie>>();
            loadPopularMovies();
        }
        return popularMovies;
    }

    private void loadAllMovie() {
        MovieInterface movieInterface = ApiService.getData().create(MovieInterface.class);
        Call<MovieResponse> call = movieInterface.getMovieList("now_playing", API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getMovies();
                allMovie.postValue(movies);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void loadPopularMovies() {
        MovieInterface movieInterface = ApiService.getData().create(MovieInterface.class);
        Call<MovieResponse> call = movieInterface.getMovieList("popular", API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getMovies();
                popularMovies.postValue(movies);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
