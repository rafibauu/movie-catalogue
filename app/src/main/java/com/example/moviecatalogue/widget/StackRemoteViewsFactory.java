package com.example.moviecatalogue.widget;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.database.FavoriteDatabase;
import com.example.moviecatalogue.interfaces.FavoriteDao;
import com.example.moviecatalogue.models.Favorite;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private List<Favorite> favoriteList = new ArrayList<>();
    private final Context mContext;
    private FavoriteDao favoriteDao;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        FavoriteDatabase database = FavoriteDatabase.getInstance(mContext);
        favoriteDao = database.favoriteDao();
        favoriteList = favoriteDao.getFavoriteToWidget();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favoriteList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        if ( favoriteList.size() != 0) {

        String url = "https://image.tmdb.org/t/p/w500/"+ favoriteList.get(position).getPoster();
        rv.setImageViewBitmap(R.id.imageView, getImageBitmap(url));
        rv.setTextViewText(R.id.textWidget, favoriteList.get(position).getTitle());

        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        }

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("", "Error getting bitmap", e);
        }
        return bm;
    }

}
