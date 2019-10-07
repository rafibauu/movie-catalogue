package com.example.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.moviecatalogue.database.FavoriteDatabase;
import com.example.moviecatalogue.interfaces.FavoriteDao;

import org.jetbrains.annotations.Nullable;

public class FavoriteContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.moviecatalogue.provider";
    public static final String TB_NAME = "favorite";
    private static final int CODE_DIR = 1;
    private static final int CODE_ITEM = 2;
    public static final Uri URI_FAVORITE = Uri.parse("content://" + AUTHORITY + "/" + TB_NAME);
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, TB_NAME, CODE_DIR);
        MATCHER.addURI(AUTHORITY, TB_NAME + "/*", CODE_ITEM);
    }

    FavoriteDatabase db;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_DIR || code == CODE_ITEM){
            final Context context = getContext();
            if (context == null) {
                return null;
            }

            db = FavoriteDatabase.getInstance(context);
            FavoriteDao favoriteDao = db.favoriteDao();
            final Cursor cursor;

            if (code == CODE_DIR) {
                cursor = favoriteDao.selectAll();
            } else {
                cursor = favoriteDao.selectAll();
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        }else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
