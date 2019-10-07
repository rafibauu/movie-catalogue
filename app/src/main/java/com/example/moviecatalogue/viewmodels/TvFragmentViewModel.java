package com.example.moviecatalogue.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalogue.interfaces.TvInterface;
import com.example.moviecatalogue.models.Tv;
import com.example.moviecatalogue.models.TvResponse;
import com.example.moviecatalogue.services.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvFragmentViewModel extends ViewModel {

    private static final String API_KEY = "ea8aa5120eed2fdb1d312cf637abf995";

    private MutableLiveData<List<Tv>> allTv;
    private MutableLiveData<List<Tv>> popularTv;
    private MutableLiveData<List<Tv>> searchTv;
    private MutableLiveData<List<Tv>> newRelease;

    public LiveData<List<Tv>> getTvData() {
        if (allTv == null) {
            allTv = new MutableLiveData<List<Tv>>();
            loadTvData();
        }
        return allTv;
    }

    public LiveData<List<Tv>> getPopularTvs() {
        if (popularTv == null) {
            popularTv = new MutableLiveData<List<Tv>>();
            loadPopularTvs();
        }
        return popularTv;
    }

    public LiveData<List<Tv>> searchMovie(String query) {
        searchTv = new MutableLiveData<List<Tv>>();
        loadSearchTv(query);
        return searchTv;
    }

    public LiveData<List<Tv>> newRelease() {
        if (newRelease == null) {
            newRelease = new MutableLiveData<List<Tv>>();
            loadNewRelease();
        }
        return newRelease;
    }

    private void loadTvData() {
        TvInterface tvInterface = ApiService.getData().create(TvInterface.class);
        Call<TvResponse> call = tvInterface.getTvList("airing_today", API_KEY);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                List<Tv> Tvs = response.body().getTvs();
                allTv.postValue(Tvs);
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void loadPopularTvs() {
        TvInterface tvInterface = ApiService.getData().create(TvInterface.class);
        Call<TvResponse> call = tvInterface.getTvList("popular", API_KEY);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                List<Tv> Tvs = response.body().getTvs();
                popularTv.postValue(Tvs);
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void loadSearchTv(String query) {
        TvInterface tvInterface = ApiService.getData().create(TvInterface.class);
        Call<TvResponse> call = tvInterface.searchTv(API_KEY, query);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                List<Tv> movies = response.body().getTvs();
                searchTv.postValue(movies);
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void loadNewRelease() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String date = dateFormat.format(calendar.getTime());
        TvInterface tvInterface = ApiService.getData().create(TvInterface.class);
        Call<TvResponse> call = tvInterface.newRelease(API_KEY, date, date);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                List<Tv> tvs = response.body().getTvs();
                newRelease.postValue(tvs);
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
