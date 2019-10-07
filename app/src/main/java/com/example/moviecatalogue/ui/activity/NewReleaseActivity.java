package com.example.moviecatalogue.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.models.Tv;
import com.example.moviecatalogue.ui.adapters.MovieAdapter;
import com.example.moviecatalogue.ui.adapters.TvAdapter;
import com.example.moviecatalogue.viewmodels.MovieFragmentViewModel;
import com.example.moviecatalogue.viewmodels.TvFragmentViewModel;

import java.util.List;

public class NewReleaseActivity extends AppCompatActivity {

    private RecyclerView rcNewRelease;
    private MovieAdapter faNewRelease;
    private MovieFragmentViewModel vmAllMovie;
    private ProgressBar pbMovieNewRelease;

    private RecyclerView rcTvNewRelease;
    private TvAdapter faTvNewRelease;
    private TvFragmentViewModel vmTvNewRelease;
    private ProgressBar pbTvNewRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_release);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightGrey));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        getSupportActionBar().setTitle(R.string.new_release);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        getSupportActionBar().setElevation(0);

        rcNewRelease = findViewById(R.id.movieNewReleaseList);
        rcNewRelease.setHasFixedSize(true);
        rcNewRelease.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcNewRelease.setNestedScrollingEnabled(true);

        faNewRelease = new MovieAdapter(this);
        faNewRelease.notifyDataSetChanged();

        pbMovieNewRelease = findViewById(R.id.movieNewReleaseProgressbar);
        setProgressBarState(pbMovieNewRelease, true);

        vmAllMovie = ViewModelProviders.of(this).get(MovieFragmentViewModel.class);
        vmAllMovie.newRelease().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                setProgressBarState(pbMovieNewRelease, false);
                faNewRelease.setMovieList(movies);
                rcNewRelease.setAdapter(faNewRelease);
            }
        });

        rcTvNewRelease = findViewById(R.id.tvNewReleaseList);
        rcTvNewRelease.setHasFixedSize(true);
        rcTvNewRelease.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcTvNewRelease.setNestedScrollingEnabled(true);

        faTvNewRelease = new TvAdapter(this);
        faTvNewRelease.notifyDataSetChanged();

        pbTvNewRelease = findViewById(R.id.tvNewReleaseProgressbar);
        setProgressBarState(pbTvNewRelease, true);

        vmTvNewRelease = ViewModelProviders.of(this).get(TvFragmentViewModel.class);
        vmTvNewRelease.newRelease().observe(this, new Observer<List<Tv>>() {
            @Override
            public void onChanged(@Nullable List<Tv> tvs) {
                setProgressBarState(pbTvNewRelease, false);
                faTvNewRelease.setTvList(tvs);
                rcTvNewRelease.setAdapter(faTvNewRelease);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setProgressBarState(ProgressBar progressBar, Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
