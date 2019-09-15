package com.example.moviecatalogue.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.ui.adapters.ImageSliderAdapter;
import com.example.moviecatalogue.ui.adapters.MovieAdapter;
import com.example.moviecatalogue.viewmodels.MovieFragmentViewModel;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static final String TAG = "MovieFragment";

    private MovieAdapter movieAdapter;
    private RecyclerView rvMovie;
    private ProgressBar pbNowShowing;
    private MovieFragmentViewModel vmAllMovie;

    private MovieAdapter pupolarMoviesAdapter;
    private RecyclerView rvPopularMovies;
    private ProgressBar pbPopularMovies;
    private MovieFragmentViewModel vmPopularMovies;

    ViewPager viewPager;

    CarouselView carouselView;
    String[] sampleImages = {"https://image.tmdb.org/t/p/w500/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg", "https://image.tmdb.org/t/p/w500/m67smI1IIMmYzCl9axvKNULVKLr.jpg"};

    public ImageView setting;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        view.setNestedScrollingEnabled(true);

        // Setting onClick Listener
//        setting = view.findViewById(R.id.setting);
//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                getActivity().startActivity(intent);
//            }
//        });

//        viewPager = view.findViewById(R.id.imageSlider);
//        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(getActivity());
//        viewPager.setAdapter(imageSliderAdapter);

        // Carousel
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        // Now Playing Movie
        rvMovie = view.findViewById(R.id.movieList);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        pbNowShowing = view.findViewById(R.id.nowShowingProgressbar);

        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        vmAllMovie = ViewModelProviders.of(getActivity()).get(MovieFragmentViewModel.class);
        vmAllMovie.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter.setMovieList(movies);
                rvMovie.setAdapter(movieAdapter);
                latestProgressBar(false);
            }
        });

        // Popular Movie
        rvPopularMovies = view.findViewById(R.id.poopularMovies);
        rvPopularMovies.setHasFixedSize(true);
        rvPopularMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        pbPopularMovies = view.findViewById(R.id.popularProgressBar);

        pupolarMoviesAdapter = new MovieAdapter(getActivity());
        pupolarMoviesAdapter.notifyDataSetChanged();

        vmPopularMovies = ViewModelProviders.of(getActivity()).get(MovieFragmentViewModel.class);
        vmPopularMovies.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                pupolarMoviesAdapter.setMovieList(movies);
                rvPopularMovies.setAdapter(pupolarMoviesAdapter);
                popularProgressBar(false);
            }
        });

        return view;

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso
                    .get()
                    .load(sampleImages[position])
                    .fit()
                    .into(imageView);
        }
    };

    private void latestProgressBar(Boolean state) {
        if (state) {
            pbNowShowing.setVisibility(View.VISIBLE);
        } else {
            pbNowShowing.setVisibility(View.GONE);
        }
    }

    private void popularProgressBar(Boolean state) {
        if (state) {
            pbPopularMovies.setVisibility(View.VISIBLE);
        } else {
            pbPopularMovies.setVisibility(View.GONE);
        }
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

        }
    }

}
