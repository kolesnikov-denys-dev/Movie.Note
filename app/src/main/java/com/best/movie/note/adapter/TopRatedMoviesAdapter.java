package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.MovieTopRatedHorizontalSmallItemBinding;
import com.best.movie.note.model.movies.toprated.TopRatedResult;

import java.util.ArrayList;


public class TopRatedMoviesAdapter extends RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMoviesViewHolder> {

    private ArrayList<TopRatedResult> moviesList;

    public TopRatedMoviesAdapter(ArrayList<TopRatedResult> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public TopRatedMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieTopRatedHorizontalSmallItemBinding movieVerticalItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.movie_top_rated_horizontal_small_item, parent, false);
        return new TopRatedMoviesViewHolder(movieVerticalItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMoviesViewHolder holder, int position) {
        holder.resultListItemBinding.setTopRatedResult(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class TopRatedMoviesViewHolder extends RecyclerView.ViewHolder {
        private MovieTopRatedHorizontalSmallItemBinding resultListItemBinding;

        public TopRatedMoviesViewHolder(@NonNull MovieTopRatedHorizontalSmallItemBinding resultListItemBinding) {
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
