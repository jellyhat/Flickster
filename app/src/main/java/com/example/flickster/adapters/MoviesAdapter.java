package com.example.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.example.flickster.DetailActivity;
import com.example.flickster.R;
import com.example.flickster.module.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
        RelativeLayout container;

        public ViewHolder( View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.Title);
            tvOverview = itemView.findViewById(R.id.Overview);
            ivPoster = itemView.findViewById(R.id.idPoster);
            container = itemView.findViewById(R.id.container);
        }


        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageURL = movie.getPosterPath();
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
            }
            //Glide.with(context).load(imageURL).into(ivPoster);
            Glide.with(context)
                    .load(imageURL)
                    .apply(new RequestOptions().transform(new RoundedCornersTransformation(50, 2)))
                    .into(ivPoster);

            //if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                container.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, DetailActivity.class);
                        //i.putExtra("title", movie.getTitle());
                        i.putExtra("movie", Parcels.wrap(movie));
                        context.startActivity(i);
                    }

                });
           // }

        }
    }
}
