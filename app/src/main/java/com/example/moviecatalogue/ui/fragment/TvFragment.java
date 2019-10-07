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
import com.example.moviecatalogue.models.Tv;
import com.example.moviecatalogue.ui.activity.NewReleaseActivity;
import com.example.moviecatalogue.ui.adapters.CategoryAdapter;
import com.example.moviecatalogue.ui.adapters.TvAdapter;
import com.example.moviecatalogue.viewmodels.TvFragmentViewModel;
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
public class TvFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private RecyclerView rvCategory;
    private List<String> categories;

    private TvAdapter tvAdapter;
    private RecyclerView rvOnAirTv;
    private ProgressBar pbOnAir;
    private TvFragmentViewModel vmAllTvs;

    private TvAdapter popularTvAdapter;
    private RecyclerView rvPopularTv;
    private ProgressBar pbPopularTv;
    private TvFragmentViewModel vmPopularTvs;

    private CarouselView carouselView;
    private List<Tv> releaseToday;
    private ProgressBar pbCarousel;
    private TvFragmentViewModel vmCarouselView;

    public ImageView setting;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        view.setNestedScrollingEnabled(true);

        Toolbar toolbar = view.findViewById(R.id.tvFragmentToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tv Show");
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        // Carousel
        carouselView = view.findViewById(R.id.carouselView);
        pbCarousel = view.findViewById(R.id.carouselProgressbar);
        vmCarouselView = ViewModelProviders.of(this).get(TvFragmentViewModel.class);
        vmCarouselView.newRelease().observe(this, new Observer<List<Tv>>() {
            @Override
            public void onChanged(@Nullable List<Tv> tvs) {
                if ( releaseToday == null ) {
                    releaseToday = tvs;
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

        // Airing Today
        rvOnAirTv = view.findViewById(R.id.onAirTvList);
        rvOnAirTv.setHasFixedSize(true);
        rvOnAirTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        pbOnAir = view.findViewById(R.id.onAirProgressbar);

        tvAdapter = new TvAdapter(getActivity());
        tvAdapter.notifyDataSetChanged();

        vmAllTvs = ViewModelProviders.of(getActivity()).get(TvFragmentViewModel.class);
        vmAllTvs.getTvData().observe(this, new Observer<List<Tv>>() {
            @Override
            public void onChanged(@Nullable List<Tv> tvs) {
                tvAdapter.setTvList(tvs);
                rvOnAirTv.setAdapter(tvAdapter);
                setProgressBarState(pbOnAir, false);
            }
        });

        // Popular
        rvPopularTv = view.findViewById(R.id.popularTvList);
        rvPopularTv.setHasFixedSize(true);
        rvPopularTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        pbPopularTv = view.findViewById(R.id.popularTvProgressbar);

        popularTvAdapter = new TvAdapter(getActivity());
        popularTvAdapter.notifyDataSetChanged();

        vmPopularTvs = ViewModelProviders.of(getActivity()).get(TvFragmentViewModel.class);
        vmPopularTvs.getPopularTvs().observe(this, new Observer<List<Tv>>() {
            @Override
            public void onChanged(@Nullable List<Tv> tvs) {
                popularTvAdapter.setTvList(tvs);
                rvPopularTv.setAdapter(popularTvAdapter);
                setProgressBarState(pbPopularTv, false);
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
