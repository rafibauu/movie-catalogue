package com.example.moviecatalogue.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.ui.activity.NewReleaseActivity;
import com.example.moviecatalogue.ui.adapters.CategoryAdapter;
import com.example.moviecatalogue.ui.adapters.MovieAdapter;
import com.example.moviecatalogue.viewmodels.MovieFragmentViewModel;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static final String TAG = "MovieFragment";

    private CategoryAdapter categoryAdapter;
    private RecyclerView rvCategory;
    private List<String> categories;

    private MovieAdapter movieAdapter;
    private RecyclerView rvMovie;
    private ProgressBar pbNowShowing;
    private MovieFragmentViewModel vmAllMovie;

    private MovieAdapter pupolarMoviesAdapter;
    private RecyclerView rvPopularMovies;
    private ProgressBar pbPopularMovies;
    private MovieFragmentViewModel vmPopularMovies;

    private CarouselView carouselView;
    private List<Movie> releaseToday;
    private ProgressBar pbCarousel;
    private MovieFragmentViewModel vmCarouselView;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

//        Window window = getActivity().getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        Toolbar toolbar = view.findViewById(R.id.movieFragmentToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Movie");
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        // Carousel
        carouselView = view.findViewById(R.id.carouselView);
        pbCarousel = view.findViewById(R.id.carouselProgressbar);
        vmCarouselView = ViewModelProviders.of(this).get(MovieFragmentViewModel.class);
        vmCarouselView.newRelease().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if ( releaseToday == null ) {
                    releaseToday = movies;
                    carouselView.setViewListener(viewListener);
                    carouselView.setPageCount(releaseToday.size());
                    setProgressBarState(pbCarousel,false);
                }
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent newRelease = new Intent(getActivity(), NewReleaseActivity.class);
                startActivity(newRelease);
            }
        });

        // Category
        categories = new ArrayList<String>(Arrays.asList("action", "mystery", "musical", "scifi", "horror", "sport", "thriller", "comedy"));

        rvCategory = view.findViewById(R.id.categoryList);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        categoryAdapter = new CategoryAdapter(getActivity(), getFragmentManager());
        categoryAdapter.setMovieList(categories);
        rvCategory.setAdapter(categoryAdapter);

        // Now Playing Movie
        rvMovie = view.findViewById(R.id.movieList);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMovie.setNestedScrollingEnabled(true);

        pbNowShowing = view.findViewById(R.id.nowShowingProgressbar);

        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        vmAllMovie = ViewModelProviders.of(getActivity()).get(MovieFragmentViewModel.class);
        vmAllMovie.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter.setMovieList(movies);
                rvMovie.setAdapter(movieAdapter);
                setProgressBarState(pbNowShowing,false);
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
                setProgressBarState(pbPopularMovies,false);
            }
        });

        return view;

    }

    public ViewListener viewListener = new ViewListener() {

        ImageView backdrop;
        TextView title;
        TextView description;

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.image_slider_item, null);

            title = customView.findViewById(R.id.itemTitle);
            title.setText(releaseToday.get(position).getName());

            description = customView.findViewById(R.id.itemDescription);
            description.setText(releaseToday.get(position).getDescription());

            String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
            backdrop = customView.findViewById(R.id.imageSliderItem);
            Picasso
                    .get()
                    .load( IMAGE_PATH + releaseToday.get(position).getBackground())
                    .placeholder(R.drawable.image_placeholder)
                    .fit()
                    .into(backdrop);

            return customView;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.library_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                SettingFragment settingFragment = new SettingFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, settingFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setProgressBarState(ProgressBar progressBar, Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
