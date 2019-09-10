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
import com.example.moviecatalogue.models.Tv;
import com.example.moviecatalogue.ui.activity.TvDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private List<Tv> tvList;

    public TvAdapter(Context context) {
        this.context = context;
    }

    public List<Tv> getTvList() {
        return tvList;
    }

    public void setTvList(List<Tv> tvList) {
//        tvList.clear();
//        tvList.addAll(items);
        this.tvList = tvList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_item_view, parent, false);
        return new TvAdapter.ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.tvName.setText(getTvList().get(i).getName());
        viewHolder.tvLanguange.setText(getTvList().get(i).getLanguage());
        viewHolder.tvRating.setText(getTvList().get(i).getRating().toString());

        Picasso
                .get()
                .load(IMAGE_PATH + getTvList().get(i).getPoster())
                .placeholder(R.drawable.image_placeholder)
                .fit()
                .into(viewHolder.tvPoster);

        viewHolder.tvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TvDetailActivity.class);
                intent.putExtra(TvDetailActivity.EXTRA_MOVIE, tvList.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getTvList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout tvContainer;
        TextView tvName;
        TextView tvLanguange;
        TextView tvRating;
        ImageView tvPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContainer = itemView.findViewById(R.id.itemContainer);
            tvName = itemView.findViewById(R.id.itemName);
            tvLanguange = itemView.findViewById(R.id.itemLanguage);
            tvRating = itemView.findViewById(R.id.itemRating);
            tvPoster = itemView.findViewById(R.id.itemPoster);

        }
    }
}
