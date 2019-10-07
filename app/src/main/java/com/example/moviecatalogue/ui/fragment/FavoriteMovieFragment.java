package com.example.moviecatalogue.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.provider.FavoriteFactory;
import com.example.moviecatalogue.models.Favorite;
import com.example.moviecatalogue.ui.adapters.FavoriteAdapter;
import com.example.moviecatalogue.viewmodels.LibraryFragmentViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {

    private FavoriteAdapter favoriteAdapter;
    private RecyclerView rvFavorite;
    private LibraryFragmentViewModel vmAllFavorite;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        view.setNestedScrollingEnabled(true);

        rvFavorite = view.findViewById(R.id.list);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorite.setHasFixedSize(true);

        favoriteAdapter = new FavoriteAdapter(getContext());

        vmAllFavorite = ViewModelProviders.of(getActivity(),
                new FavoriteFactory(getActivity().getApplication())).get(LibraryFragmentViewModel.class);
//        vmAllFavorite = ViewModelProviders.of(getActivity()).get(LibraryFragmentViewModel.class);
        vmAllFavorite.getAllFavoriteMovies().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                favoriteAdapter.setFavoriteList(favorites);
                rvFavorite.setAdapter(favoriteAdapter);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                vmAllFavorite.delete(favoriteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvFavorite);
        return view;
    }

}
