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
import com.example.moviecatalogue.models.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private List<Favorite> favoriteList;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public List<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Favorite> items) {
        this.favoriteList = items;
        notifyDataSetChanged();
    }

    public Favorite getNoteAt(int i) {
        return favoriteList.get(i);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View rowItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Favorite favorite = getFavoriteList().get(i);
        viewHolder.title.setText(favorite.getTitle());
        Picasso
                .get()
                .load(IMAGE_PATH + favorite.getPoster())
                .placeholder(R.drawable.image_placeholder)
                .fit()
                .into(viewHolder.poster);

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView language;
        TextView title;
        TextView status;
        TextView releaseDate;
        TextView runtime;
        TextView rating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster);
            language = itemView.findViewById(R.id.language);
            title = itemView.findViewById(R.id.title);
            status = itemView.findViewById(R.id.status);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            runtime = itemView.findViewById(R.id.runtime);
            rating = itemView.findViewById(R.id.rating);

        }
    }
}
