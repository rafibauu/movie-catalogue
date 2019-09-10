package com.example.moviecatalogue.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moviecatalogue.models.Favorite;
import com.example.moviecatalogue.repositories.FavoriteRepository;

import java.util.List;

public class LibraryFragmentViewModel extends AndroidViewModel {

    private FavoriteRepository repository;
    private LiveData<List<Favorite>> allFavorites;
    private LiveData<List<Favorite>> allfavoriteMovies;
    private LiveData<List<Favorite>> allfavoriteTvs;

    public LibraryFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allFavorites = repository.getAllFavorites();
        allfavoriteMovies = repository.getAllFavoriteMovies();
        allfavoriteTvs = repository.getAllFavoriteTvs();
    }

    public void insert(Favorite favorite) {
        repository.insert(favorite);
    }

    public LiveData<List<Favorite>> getFavorite(int id) { return repository.getFavorite(id); }

    public void update(Favorite favorite) {
        repository.update(favorite);
    }

    public void delete(Favorite favorite) {
        repository.delete(favorite);
    }

    public void deleteAllFavorites() {
        repository.deleteAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavoriteMovies() { return allfavoriteMovies; }

    public LiveData<List<Favorite>> getAllFavoriteTvs() { return allfavoriteTvs; }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }
}
