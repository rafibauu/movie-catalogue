package com.example.moviecatalogue.ui.activity;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Favorite;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.viewmodels.LibraryFragmentViewModel;
import com.example.moviecatalogue.widget.FavoriteMovieWidget;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.moviecatalogue.R.drawable.gradient_placeholder_image;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    public static final String EXTRA_MOVIE = "extra_movie";

    Movie movie;
    TextView movieTitle;
    TextView movieRuntime;
    TextView movieDesc;
    TextView movieRating;
    TextView movieVoteCount;
    TextView movieReleaseDate;
    TextView genre1;
    TextView genre2;
    TextView genre3;
    ImageView moviePoster;
    ImageView movieBackground;
    ImageView favoriteButton;
    ImageView unfavoriteButton;
    LibraryFragmentViewModel vmAllFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightGrey));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//
        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        favoriteButton = findViewById(R.id.favoriteButton);
        unfavoriteButton = findViewById(R.id.unfavoriteButton);

        vmAllFavorite = ViewModelProviders.of(this).get(LibraryFragmentViewModel.class);
        vmAllFavorite.getFavorite(movie.getId()).observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                if (favorites.size() != 0) {
                    unfavoriteButton.setVisibility(View.VISIBLE);
                    favoriteButton.setVisibility(View.GONE);
                } else {
                    unfavoriteButton.setVisibility(View.GONE);
                    favoriteButton.setVisibility(View.VISIBLE);
                }
            }
        });

        moviePoster = findViewById(R.id.moviesPoster);
        loadPoster(movie.getPoster(), moviePoster);
        setProgressBar(findViewById(R.id.posterProgressBar), false);

        movieBackground = findViewById(R.id.moviesBackground);
        loadPoster(movie.getBackground(), movieBackground);

        movieRuntime = findViewById(R.id.moviesRuntime);
        genre1 = findViewById(R.id.genre1);
        genre2 = findViewById(R.id.genre2);
        genre3 = findViewById(R.id.genre3);

        movieTitle = findViewById(R.id.movieTitle);
        movieTitle.setText(movie.getName());

        movieRating = findViewById(R.id.itemRating);
        movieRating.setText(movie.getRating().toString());

        movieReleaseDate = findViewById(R.id.releaseDate);
        movieReleaseDate.setText(movie.getReleaseDate());

        movieVoteCount = findViewById(R.id.voteCount);
        movieVoteCount.setText("(" + String.valueOf(movie.getVoteCount()) + " voters)");

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(movie.getId(), "movie",
                        movie.getName(), movie.getPoster(),
                        movie.getRating());
                vmAllFavorite.insert(favorite);
                Toast.makeText(getApplicationContext(), "Added to favorite", Toast.LENGTH_SHORT).show();
                updateWidget();
            }
        });

        unfavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(movie.getId(), "movie",
                        movie.getName(), movie.getPoster(),
                        movie.getRating());
                vmAllFavorite.delete(favorite);
                Toast.makeText(getApplicationContext(), "Deleted from favorite", Toast.LENGTH_SHORT).show();
                updateWidget();
            }
        });

        movieDesc = findViewById(R.id.movieDescription);
        movieDesc.setText(movie.getDescription());

        getMoviesDetail();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_item_toolbar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getMoviesDetail() {

        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "?api_key=ea8aa5120eed2fdb1d312cf637abf995";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String runtime = jsonObject.getString("runtime");
                            movieRuntime.setText(runtime + " mins");
                            JSONArray genres = jsonObject.getJSONArray("genres");

                            for (int j = 0 ; j < genres.length(); j++)
                            {
                                if ( j == 0 ) {
                                    JSONObject gen1 = genres.getJSONObject(j);
                                    genre1.setText(gen1.getString("name"));
                                    genre1.setVisibility(View.VISIBLE);
                                } else if ( j == 1 ) {
                                    JSONObject gen2 = genres.getJSONObject(j);
                                    genre2.setText(gen2.getString("name"));
                                    genre2.setVisibility(View.VISIBLE);
                                } else if ( j == 3 ) {
                                    JSONObject gen3 = genres.getJSONObject(j);
                                    genre3.setText(gen3.getString("name"));
                                    genre3.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetailActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(MovieDetailActivity.this);
        requestQueue.add(stringRequest);
    }

    private void loadPoster(String url, ImageView imageView) {
        Picasso
                .get()
                .load(IMAGE_PATH + url)
                .placeholder(R.drawable.image_placeholder)
                .fit()
                .into(imageView);
    }

    private void setProgressBar(View view, Boolean state) {
        if (state) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void updateWidget() {
        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, FavoriteMovieWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
    }
}
