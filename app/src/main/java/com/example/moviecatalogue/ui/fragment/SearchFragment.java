package com.example.moviecatalogue.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.models.Tv;
import com.example.moviecatalogue.ui.adapters.LibraryAdapter;
import com.example.moviecatalogue.ui.adapters.MovieAdapter;
import com.example.moviecatalogue.ui.adapters.TvAdapter;
import com.example.moviecatalogue.viewmodels.MovieFragmentViewModel;
import com.example.moviecatalogue.viewmodels.TvFragmentViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchView searchView;

    ProgressBar movieSearchProgressBar;

    TextView tvMovieSearching;
    TextView tvMovieNotFound;
    RecyclerView rvMovieSearch;
    MovieAdapter movieSearchAdapter;
    MovieFragmentViewModel vmSearchMovie;

    TextView tvTvSearching;
    TextView tvTvNotFound;
    RecyclerView rvTvSearch;
    TvAdapter tvSearchAdapter;
    TvFragmentViewModel vmSearchTv;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);

        Toolbar toolbar = view.findViewById(R.id.searchToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.search);

        movieSearchProgressBar = view.findViewById(R.id.searchingProgressbar);
        movieSearchProgressBar.setVisibility(View.GONE);

        tvMovieSearching = view.findViewById(R.id.movieSearchingTitle);
        tvMovieSearching.setVisibility(View.GONE);

        tvMovieNotFound = view.findViewById(R.id.movieNotFoundText);

        rvMovieSearch = view.findViewById(R.id.movieSearchingList);
        rvMovieSearch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMovieSearch.setHasFixedSize(true);
        rvMovieSearch.setNestedScrollingEnabled(true);

        movieSearchAdapter = new MovieAdapter(getActivity());
        movieSearchAdapter.notifyDataSetChanged();

        tvTvSearching = view.findViewById(R.id.tvSearchingTitle);
        tvTvSearching.setVisibility(View.GONE);

        tvTvNotFound = view.findViewById(R.id.tvNotFoundText);

        rvTvSearch = view.findViewById(R.id.tvSearchingList);
        rvTvSearch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTvSearch.setHasFixedSize(true);
        rvTvSearch.setNestedScrollingEnabled(true);

        tvSearchAdapter = new TvAdapter(getActivity());
        tvSearchAdapter.notifyDataSetChanged();

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                movieSearchProgressBar.setVisibility(View.VISIBLE);
                vmSearchMovie = ViewModelProviders.of(getActivity()).get(MovieFragmentViewModel.class);
                vmSearchMovie.searchMovie(s).observe(getActivity(), new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        tvMovieSearching.setVisibility(View.VISIBLE);
                        movieSearchAdapter.setMovieList(movies);
                        rvMovieSearch.setAdapter(movieSearchAdapter);
                        if ( movies.size() == 0 ) {
                            tvMovieNotFound.setVisibility(View.VISIBLE);
                        } else {
                            tvMovieNotFound.setVisibility(View.GONE);
                        }
                    }
                });
                vmSearchTv = ViewModelProviders.of(getActivity()).get(TvFragmentViewModel.class);
                vmSearchTv.searchMovie(s).observe(getActivity(), new Observer<List<Tv>>() {
                    @Override
                    public void onChanged(@Nullable List<Tv> tvs) {
                        tvTvSearching.setVisibility(View.VISIBLE);
                        tvSearchAdapter.setTvList(tvs);
                        rvTvSearch.setAdapter(tvSearchAdapter);
                        movieSearchProgressBar.setVisibility(View.GONE);
                        if ( tvs.size() == 0 ) {
                            tvTvNotFound.setVisibility(View.VISIBLE);
                        } else {
                            tvTvNotFound.setVisibility(View.GONE);
                        }
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.library_toolbar, menu);
//        super.onCreateOptionsMenu(menu, inflater);
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

}
