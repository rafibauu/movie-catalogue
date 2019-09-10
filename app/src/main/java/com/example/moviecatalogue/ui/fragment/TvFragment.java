package com.example.moviecatalogue.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Tv;
import com.example.moviecatalogue.ui.adapters.TvAdapter;
import com.example.moviecatalogue.viewmodels.TvFragmentViewModel;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    private TvAdapter tvAdapter;
    private RecyclerView rvOnAirTv;
    private ProgressBar pbOnAir;
    private TvFragmentViewModel vmAllTvs;

    private TvAdapter popularTvAdapter;
    private RecyclerView rvPopularTv;
    private ProgressBar pbPopularTv;
    private TvFragmentViewModel vmPopularTvs;

    CarouselView carouselView;
    String[] sampleImages = {"https://image.tmdb.org/t/p/w500/7AKhSfJHnVi0zXQS4eJirHDs22p.jpg", "https://image.tmdb.org/t/p/w500/piuRhGiQBYWgW668eSNJ2ug5uAO.jpg"};

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

        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        view.setNestedScrollingEnabled(true);

        // Setting onClick listener
//        setting = view.findViewById(R.id.setting);
//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                getActivity().startActivity(intent);
//            }
//        });

        // Carousel
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

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
                showLoading(false);
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
                    .into(imageView);
        }
    };

    private void setProgressBar(View view, Boolean state) {
        if (state) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbOnAir.setVisibility(View.VISIBLE);
        } else {
            pbOnAir.setVisibility(View.GONE);
        }
    }

    private void popularProgressBar(Boolean state) {
        if (state) {
            pbPopularTv.setVisibility(View.VISIBLE);
        } else {
            pbPopularTv.setVisibility(View.GONE);
        }
    }
}
