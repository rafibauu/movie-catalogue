package com.example.moviecatalogue.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.moviecatalogue.interfaces.FavoriteDao;
import com.example.moviecatalogue.models.Favorite;

@Database(entities = {Favorite.class}, version = 3)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if ( instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, "db_favorite")
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDdAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDdAsyncTask extends AsyncTask<Void, Void, Void> {
//        private FavoriteDao favoriteDao;
//
//        private PopulateDdAsyncTask(FavoriteDatabase db) {
//            favoriteDao = db.favoriteDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            favoriteDao.insert(new Favorite("Favorite 1", "/5vHssUeVe25bMrof1HyaPyWgaP.jpg", 7.1));
//            favoriteDao.insert(new Favorite("Favorite 2", "/5vHssUeVe25bMrof1HyaPyWgaP.jpg", 7.2));
//            favoriteDao.insert(new Favorite("Favorite 3", "/5vHssUeVe25bMrof1HyaPyWgaP.jpg", 7.3));
//            return null;
//        }
//    }
}
