package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.MovieHorizontalItemBinding;
import com.best.movie.note.databinding.MovieHorizontalSmallItemBinding;
import com.best.movie.note.databinding.MovieVerticalItemBinding;
import com.best.movie.note.model.genres.GenreResult;
import com.best.movie.note.model.movies.list.MovieResult;

import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private ArrayList<MovieResult> moviesList;
    private ArrayList<GenreResult> genres;
    private ArrayList<String> subtitleList;
    private int cardsType;
    private OnMovieClickListener onMovieClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(int movieId, String originalName);
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public MoviesAdapter(ArrayList<MovieResult> moviesList, int cardsType, ArrayList<GenreResult> genresResults) {
        this.moviesList = moviesList;
        this.cardsType = cardsType;
        this.genres = genresResults;

        subtitleList = new ArrayList<>();

        for (int i = 0; i < moviesList.size(); i++) {
            StringBuilder genres = new StringBuilder();
            List<Integer> listGenres = moviesList.get(i).getGenreIds();
            for (int j = 0; j < listGenres.size(); j++) {
                if (j < listGenres.size() - 1) {
                    genres.append(getGenreById(listGenres.get(j))).append(", ");
                } else {
                    genres.append(getGenreById(listGenres.get(j))).append(" ");
                }
            }

            if (getYear(moviesList.get(i).getReleaseDate()).length() < 3) {
                subtitleList.add(genres.toString());
            } else {
                subtitleList.add(getYear(moviesList.get(i).getReleaseDate()) + " " + genres);
            }
        }
    }


    public String getYear(String year) {
        if (year != null && year.length() > 3) {
            return year.split("-")[0];
        }
        return "";
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

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        private MovieVerticalItemBinding verticalCard;
        private MovieHorizontalItemBinding horizontalCard;
        private MovieHorizontalSmallItemBinding horizontalSmallCard;

        public MoviesViewHolder(@NonNull MovieVerticalItemBinding view) {
            super(view.getRoot());
            this.verticalCard = view;
            if (view != null) {
                verticalCard.fullConstraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMovieClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                            onMovieClickListener.onMovieClick(moviesList.get(getAdapterPosition()).getId(), moviesList.get(getAdapterPosition()).getOriginalTitle());
                        }
                    }
                });
            }
        }

        public MoviesViewHolder(@NonNull MovieHorizontalItemBinding view) {
            super(view.getRoot());
            this.horizontalCard = view;
            if (view != null) {
                horizontalCard.fullConstraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMovieClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                            onMovieClickListener.onMovieClick(moviesList.get(getAdapterPosition()).getId(), moviesList.get(getAdapterPosition()).getOriginalTitle());
                        }
                    }
                });
            }
        }

        public MoviesViewHolder(@NonNull MovieHorizontalSmallItemBinding view) {
            super(view.getRoot());
            this.horizontalSmallCard = view;
            if (view != null) {
                horizontalSmallCard.fullConstraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMovieClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                            onMovieClickListener.onMovieClick(moviesList.get(getAdapterPosition()).getId(), moviesList.get(getAdapterPosition()).getOriginalTitle());
                        }
                    }
                });
            }
        }
    }

}
