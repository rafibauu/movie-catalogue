package com.example.moviecatalogue.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Favorite;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.ui.adapters.FavoriteAdapter;
import com.example.moviecatalogue.ui.adapters.LibraryAdapter;
import com.example.moviecatalogue.ui.adapters.MovieAdapter;
import com.example.moviecatalogue.viewmodels.LibraryFragmentViewModel;
import com.example.moviecatalogue.viewmodels.MovieFragmentViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    private FavoriteAdapter favoriteAdapter;
    private RecyclerView rvFavorite;
    private LibraryFragmentViewModel vmAllFavorite;

    ViewPager viewPager;
    TabLayout tabLayout;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);
        view.setNestedScrollingEnabled(true);

        viewPager = view.findViewById(R.id.libraryViewPager);
        setUpViewPager(viewPager);

        tabLayout = view.findViewById(R.id.libraryTabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        LibraryAdapter libraryAdapter = new LibraryAdapter(getChildFragmentManager());
        libraryAdapter.addFragment(new FavoriteMovieFragment(), "Movie");
        libraryAdapter.addFragment(new FavoriteTvFragment(), "Tv Show");
        viewPager.setAdapter(libraryAdapter);
    }
}
