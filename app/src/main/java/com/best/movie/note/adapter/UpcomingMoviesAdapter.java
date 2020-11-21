package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.MovieUpcomingVerticalItemBinding;
import com.best.movie.note.model.movies.cards.MovieResult;

import java.util.ArrayList;


public class UpcomingMoviesAdapter extends RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMoviesViewHolder> {

    private ArrayList<MovieResult> moviesList;

    public UpcomingMoviesAdapter(ArrayList<MovieResult> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public UpcomingMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieUpcomingVerticalItemBinding movieVerticalItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.movie_upcoming_vertical_item, parent, false);
        return new UpcomingMoviesViewHolder(movieVerticalItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMoviesViewHolder holder, int position) {
        holder.resultListItemBinding.setUpcomingResult(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class UpcomingMoviesViewHolder extends RecyclerView.ViewHolder {
        private MovieUpcomingVerticalItemBinding resultListItemBinding;

        public UpcomingMoviesViewHolder(@NonNull MovieUpcomingVerticalItemBinding resultListItemBinding) {
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
