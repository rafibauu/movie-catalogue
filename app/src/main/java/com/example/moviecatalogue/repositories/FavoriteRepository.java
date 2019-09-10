package com.example.moviecatalogue.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.moviecatalogue.database.FavoriteDatabase;
import com.example.moviecatalogue.interfaces.FavoriteDao;
import com.example.moviecatalogue.models.Favorite;

import java.util.List;

public class FavoriteRepository {

    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allFavorites;
    private LiveData<List<Favorite>> allFavoriteMovies;
    private LiveData<List<Favorite>> allFavoriteTvs;

    public FavoriteRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        favoriteDao = database.favoriteDao();
        allFavorites = favoriteDao.getAllFavorite();
        allFavoriteMovies = favoriteDao.getAllFavoriteMovies();
        allFavoriteTvs = favoriteDao.getAllFavoriteTvs();
    }

    public void insert(Favorite favorite) {
        new InsertFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void update(Favorite favorite) {
        new UpdateFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void delete(Favorite favorite) {
        new DeleteFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void deleteAllFavorites() {
        new DeleteAllFavoriteAsyncTask(favoriteDao).execute();
    }

    public LiveData<List<Favorite>> getFavorite(int id) { return favoriteDao.getFavorite(id); }

    public LiveData<List<Favorite>> getAllFavoriteMovies() { return allFavoriteMovies; }

    public LiveData<List<Favorite>> getAllFavoriteTvs() { return allFavoriteTvs; }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }

    private static class InsertFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void > {

        private FavoriteDao favoriteDao;

        private InsertFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.insert(favorites[0]);
            return null;
        }
    }

    private static class UpdateFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void > {

        private FavoriteDao favoriteDao;

        private UpdateFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.update(favorites[0]);
            return null;
        }
    }

    private static class DeleteFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void > {

        private FavoriteDao favoriteDao;

        private DeleteFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.delete(favorites[0]);
            return null;
        }
    }

    private static class DeleteAllFavoriteAsyncTask extends AsyncTask<Void, Void, Void > {

        private FavoriteDao favoriteDao;

        private DeleteAllFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDao.deleteAllFavorites();
            return null;
        }
    }
}
