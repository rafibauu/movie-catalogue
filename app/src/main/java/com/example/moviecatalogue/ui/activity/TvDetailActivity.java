package com.example.moviecatalogue.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Favorite;
import com.example.moviecatalogue.models.Tv;
import com.example.moviecatalogue.viewmodels.LibraryFragmentViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TvDetailActivity extends AppCompatActivity {

    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    public static final String EXTRA_MOVIE = "extra_movie";

    Tv tv;
    TextView movieTitle;
    TextView movieRuntime;
    TextView movieDesc;
    TextView movieRating;
    TextView movieVoteCount;
    TextView movieReleaseDate;
    ImageView moviePoster;
    ImageView movieBackground;
    ImageView favoriteButton;
    ImageView unfavoriteButton;
    LibraryFragmentViewModel vmAllFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightGrey));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tv = getIntent().getParcelableExtra(EXTRA_MOVIE);

        favoriteButton = findViewById(R.id.favoriteButton);
        unfavoriteButton = findViewById(R.id.unfavoriteButton);

        vmAllFavorite = ViewModelProviders.of(this).get(LibraryFragmentViewModel.class);
        vmAllFavorite.getFavorite(tv.getId()).observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                if ( favorites.size() != 0 ) {
                    unfavoriteButton.setVisibility(View.VISIBLE);
                    favoriteButton.setVisibility(View.GONE);
                } else {
                    unfavoriteButton.setVisibility(View.GONE);
                    favoriteButton.setVisibility(View.VISIBLE);
                }
            }
        });

        moviePoster = findViewById(R.id.moviesPoster);
        loadPoster(tv.getPoster(), moviePoster);
        setProgressBar(findViewById(R.id.posterProgressBar), false);

        movieBackground = findViewById(R.id.moviesBackground);
        loadPoster(tv.getBackground(), movieBackground);

        movieRuntime = findViewById(R.id.moviesRuntime);

        movieTitle = findViewById(R.id.movieTitle);
        movieTitle.setText(tv.getName());

        movieRating = findViewById(R.id.itemRating);
        movieRating.setText(tv.getRating().toString());

        movieReleaseDate = findViewById(R.id.releaseDate);
        movieReleaseDate.setText(tv.getReleaseDate());

        movieVoteCount = findViewById(R.id.voteCount);
        movieVoteCount.setText("(" + String.valueOf(tv.getVoteCount()) + " voters)");

        movieDesc = findViewById(R.id.movieDescription);
        movieDesc.setText(tv.getDescription());

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(tv.getId(), "tv",
                        tv.getName(), tv.getPoster(),
                        tv.getRating());
                vmAllFavorite.insert(favorite);
                Toast.makeText(getApplicationContext(), "Added to favorite", Toast.LENGTH_SHORT).show();
            }
        });

        unfavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(tv.getId(), "tv",
                        tv.getName(), tv.getPoster(),
                        tv.getRating());
                vmAllFavorite.delete(favorite);
                Toast.makeText(getApplicationContext(), "Deleted from favorite", Toast.LENGTH_SHORT).show();
            }
        });

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

}
