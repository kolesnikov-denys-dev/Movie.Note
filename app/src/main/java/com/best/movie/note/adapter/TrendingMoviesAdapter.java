package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.MovieTrendingVerticalItemBinding;
import com.best.movie.note.model.movies.cards.MovieResult;

import java.util.ArrayList;


public class TrendingMoviesAdapter extends RecyclerView.Adapter<TrendingMoviesAdapter.PopularMoviesViewHolder> {

    private ArrayList<MovieResult> moviesList;

    public TrendingMoviesAdapter(ArrayList<MovieResult> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public PopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieTrendingVerticalItemBinding movieVerticalItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.movie_trending_vertical_item, parent, false);
        return new PopularMoviesViewHolder(movieVerticalItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesViewHolder holder, int position) {
        holder.resultListItemBinding.setMovieResult(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class PopularMoviesViewHolder extends RecyclerView.ViewHolder {
        private MovieTrendingVerticalItemBinding resultListItemBinding;

        public PopularMoviesViewHolder(@NonNull MovieTrendingVerticalItemBinding resultListItemBinding) {
            super(resultListItemBinding.getRoot());
            this.resultListItemBinding = resultListItemBinding;

            resultListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Result result = getItem(position);
//                        Intent intent = new Intent(context, MovieDetailsActivity.class);
//                        intent.putExtra("movieData", result);
//                        context.startActivity(intent);
//                    }
                }
            });
        }
    }

}
