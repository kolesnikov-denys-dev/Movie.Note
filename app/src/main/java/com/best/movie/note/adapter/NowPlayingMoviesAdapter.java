package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.MovieHorizontalItemBinding;
import com.best.movie.note.model.movies.cards.MovieResult;

import java.util.ArrayList;


public class NowPlayingMoviesAdapter extends RecyclerView.Adapter<NowPlayingMoviesAdapter.NowPlayingMoviesViewHolder> {

    private ArrayList<MovieResult> moviesList;

    public NowPlayingMoviesAdapter(ArrayList<MovieResult> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public NowPlayingMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieHorizontalItemBinding movieHorizontalItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.movie_horizontal_item, parent, false);
        return new NowPlayingMoviesViewHolder(movieHorizontalItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingMoviesViewHolder holder, int position) {
        holder.movieHorizontalItemBinding.setMovieResult(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class NowPlayingMoviesViewHolder extends RecyclerView.ViewHolder {
        private MovieHorizontalItemBinding movieHorizontalItemBinding;

        public NowPlayingMoviesViewHolder(@NonNull MovieHorizontalItemBinding movieHorizontalItemBinding) {
            super(movieHorizontalItemBinding.getRoot());
            this.movieHorizontalItemBinding = movieHorizontalItemBinding;

            movieHorizontalItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
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
