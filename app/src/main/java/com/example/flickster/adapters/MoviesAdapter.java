package com.example.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flickster.R;
import com.example.flickster.module.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    @NonNull
    Context context;
    List<Movie> movies;

    public MoviesAdapter(@NonNull Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("test", "OnCreateViewHolder");//once
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d("Test","onBindViewHolder: " + position); // many
        Movie movie = movies.get(position);
        //bind movie data to the view holder
        viewHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        public ViewHolder( View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.Title);
            tvOverview = itemView.findViewById(R.id.Overview);
            ivPoster = itemView.findViewById(R.id.idPoster);
        }


        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageURL = movie.getPosterPath();
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
            }
            Glide.with(context).load(imageURL).into(ivPoster);


        }
    }
}
