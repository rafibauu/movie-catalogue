package com.example.moviecatalogue.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.ui.activity.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> items) {
//        movieList.clear();
//        movieList.addAll(items);
        this.movieList = items;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_item_view, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.movieTitle.setText(getMovieList().get(i).getName());
        viewHolder.movieLanguage.setText(getMovieList().get(i).getLanguage());
        viewHolder.movieRating.setText(getMovieList().get(i).getRating().toString());

        Picasso
                .get()
                .load(IMAGE_PATH + getMovieList().get(i).getPoster())
                .placeholder(R.drawable.image_placeholder)
                .fit()
                .into(viewHolder.moviePoster);

        viewHolder.movieContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieList.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout movieContainer;
        TextView movieTitle;
        TextView movieLanguage;
        TextView movieRating;
        ImageView moviePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieContainer = itemView.findViewById(R.id.itemContainer);
            movieTitle = itemView.findViewById(R.id.itemName);
            movieLanguage = itemView.findViewById(R.id.itemLanguage);
            movieRating = itemView.findViewById(R.id.itemRating);
            moviePoster = itemView.findViewById(R.id.itemPoster);

        }

    }

}
