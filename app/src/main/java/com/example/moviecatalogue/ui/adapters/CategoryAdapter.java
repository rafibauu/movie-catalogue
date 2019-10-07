package com.example.moviecatalogue.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private List<String> movieCategoryList;

    public CategoryAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public List<String> getCategoryList() {
        return movieCategoryList;
    }

    public void setMovieList(List<String> categories) {
        this.movieCategoryList = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        String title = getCategoryList().get(i);
        String capitalize = title.substring(0,1).toUpperCase() + title.substring(1);
        viewHolder.categoryTitle.setText(capitalize);
        int resID = context.getResources().getIdentifier("ic_" + getCategoryList().get(i).toString(), "drawable", context.getPackageName());
        Glide.with(context)
                .load(resID)
                .into(viewHolder.categoryIcon);

        viewHolder.categoryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Category " + movieCategoryList.get(i), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout categoryContainer;
        TextView categoryTitle;
        ImageView categoryIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryContainer = itemView.findViewById(R.id.categoryContainer);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);

        }

    }

}
