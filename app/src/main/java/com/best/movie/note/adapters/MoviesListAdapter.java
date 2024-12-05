package com.best.movie.note.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.ItemMovieHorizontalListBinding;
import com.best.movie.note.model.response.movies.movie.MovieResult;

public class MoviesListAdapter extends PagedListAdapter<MovieResult, MoviesListAdapter.ResultViewHolder> {
    public MoviesListAdapter() {
        super(MovieResult.CALLBACK);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieHorizontalListBinding movieHorizontalListItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_movie_horizontal_list, parent, false);
        return new ResultViewHolder(movieHorizontalListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        MovieResult result = getItem(position);
        holder.movieHorizontalListItemBinding.setMovieResult(result);
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieHorizontalListBinding movieHorizontalListItemBinding;

        public ResultViewHolder(@NonNull ItemMovieHorizontalListBinding movieHorizontalListItemBinding) {
            super(movieHorizontalListItemBinding.getRoot());
            this.movieHorizontalListItemBinding = movieHorizontalListItemBinding;

//            resultListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        MovieResult result = getItem(position);
//                        Intent intent = new Intent(context, MovieDetailsActivity.class);
//                        intent.putExtra("movieData", result);
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }
    }
}