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
import com.best.movie.note.model.genres.GenreResult;
import com.best.movie.note.model.movies.cards.MovieResult;

import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private ArrayList<MovieResult> moviesList;
    private int cardsType;
    private ArrayList<GenreResult> genres;
    private ArrayList<String> subtitleList;

    public MoviesAdapter(ArrayList<MovieResult> moviesList, int cardsType, ArrayList<GenreResult> genresResults) {

        this.moviesList = moviesList;
        this.cardsType = cardsType;
        this.genres = genresResults;

        subtitleList = new ArrayList<>();

        for (int i = 0; i < moviesList.size(); i++) {
            String genres = "";
            List<Integer> listGenres = moviesList.get(i).getGenreIds();
            for (int j = 0; j < listGenres.size(); j++) {
                genres += getGenreById(listGenres.get(j)) + " ";
            }
            subtitleList.add(getYear(moviesList.get(i).getReleaseDate()) + " " + genres);
        }
    }


    public String getYear(String year) {
        return year.split("-")[0];
    }

    public String getGenreById(int genreId) {
        String genreStr = "";
        for (int i = 0; i < genres.size(); i++) {
            if (genreId == genres.get(i).getId()) {
                genreStr = genres.get(i).getName();
            }
        }
        return genreStr;
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
            holder.verticalCard.setSubtitle(subtitleList.get(position));
        } else if (holder.horizontalCard != null) {
            holder.horizontalCard.setMovieResult(moviesList.get(position));
            holder.horizontalCard.setSubtitle(subtitleList.get(position));
        } else if (holder.horizontalSmallCard != null) {
            holder.horizontalSmallCard.setMovieResult(moviesList.get(position));
            holder.horizontalSmallCard.setSubtitle(subtitleList.get(position));
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
