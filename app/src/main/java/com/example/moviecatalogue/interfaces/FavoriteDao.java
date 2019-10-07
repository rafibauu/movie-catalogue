package com.example.moviecatalogue.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.moviecatalogue.models.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT * FROM favorite WHERE id=:id")
    LiveData<List<Favorite>> getFavorite(int id);

    @Query("DELETE FROM favorite")
    void deleteAllFavorites();

    @Query("SELECT * FROM favorite where itemKind='movie' ")
    LiveData<List<Favorite>> getAllFavoriteMovies();

    @Query("SELECT * FROM favorite where itemKind='movie' ")
    List<Favorite> getFavoriteToWidget();

    @Query("SELECT * FROM favorite where itemKind='tv' ")
    LiveData<List<Favorite>> getAllFavoriteTvs();

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    LiveData<List<Favorite>> getAllFavorite();

    @Query("SELECT * FROM favorite")
    Cursor selectAll();

}