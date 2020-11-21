package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.MovieHorizontalItemBinding;
import com.best.movie.note.databinding.MovieHorizontalSmallItemBinding;
import com.best.movie.note.databinding.MovieVerticalItemBinding;
import com.best.movie.note.model.movies.cards.MovieResult;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private ArrayList<MovieResult> moviesList;
    private int cardsType;

    public MoviesAdapter(ArrayList<MovieResult> moviesList, int cardsType) {
        this.moviesList = moviesList;
        this.cardsType = cardsType;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 1 - paying now horizontal
        // 2 - top rated small
        // default - popular vertical, trending vertical, Upcoming vertical

        switch (cardsType) {
            case 1:
                MovieHorizontalItemBinding horizontalCard = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.movie_horizontal_item, parent, false);
                return new MoviesViewHolder(horizontalCard);
            case 2:
                MovieHorizontalSmallItemBinding smallHorizontalCard = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.movie_horizontal_small_item, parent, false);
                return new MoviesViewHolder(smallHorizontalCard);
            default:
                MovieVerticalItemBinding verticalCard = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.movie_vertical_item, parent, false);
                return new MoviesViewHolder(verticalCard);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        if (holder.verticalCard != null) {
            holder.verticalCard.setMovieResult(moviesList.get(position));
        } else if (holder.horizontalCard != null) {
            holder.horizontalCard.setMovieResult(moviesList.get(position));
        } else if (holder.horizontalSmallCard != null) {
            holder.horizontalSmallCard.setMovieResult(moviesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {

        private MovieVerticalItemBinding verticalCard;
        private MovieHorizontalItemBinding horizontalCard;
        private MovieHorizontalSmallItemBinding horizontalSmallCard;

        public MoviesViewHolder(@NonNull MovieVerticalItemBinding view) {
            super(view.getRoot());
            this.verticalCard = view;
        }

        public MoviesViewHolder(@NonNull MovieHorizontalItemBinding view) {
            super(view.getRoot());
            this.horizontalCard = view;
        }

        public MoviesViewHolder(@NonNull MovieHorizontalSmallItemBinding view) {
            super(view.getRoot());
            this.horizontalSmallCard = view;
        }
    }

}
