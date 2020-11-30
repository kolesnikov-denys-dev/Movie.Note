package com.best.movie.note.adapters;

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
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL;
import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL_SMALL;


public class CommonContentAdapter extends RecyclerView.Adapter<CommonContentAdapter.MoviesViewHolder> {

    private ArrayList<MovieResult> movies;
    private ArrayList<GenreResult> genres;
    private ArrayList<String> subtitles;
    private OnMovieClickListener onMovieClickListener;
    private int cardType;
    private int contentType;

    public interface OnMovieClickListener {
        void onMovieClick(int movieId, String originalName, int contentType);
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public CommonContentAdapter(ArrayList<MovieResult> movies, int cardType, ArrayList<GenreResult> genresResults, int contentType) {
        this.movies = movies;
        this.cardType = cardType;
        this.genres = genresResults;
        this.contentType = contentType;

        if (genresResults != null) {
            getSubtitles();
        }
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (cardType) {
            case CARD_TYPE_HORIZONTAL:
                MovieHorizontalItemBinding horizontalCard = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.movie_horizontal_item, parent, false);
                return new MoviesViewHolder(horizontalCard);
            case CARD_TYPE_HORIZONTAL_SMALL:
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
            holder.verticalCard.setMovieResult(movies.get(position));
            if (subtitles != null) {
                holder.verticalCard.setSubtitle(subtitles.get(position));
            }
        } else if (holder.horizontalCard != null) {
            holder.horizontalCard.setMovieResult(movies.get(position));
            if (subtitles != null) {
                holder.horizontalCard.setSubtitle(subtitles.get(position));
            }
        } else if (holder.horizontalSmallCard != null) {
            holder.horizontalSmallCard.setMovieResult(movies.get(position));
            if (subtitles != null) {
                holder.horizontalSmallCard.setSubtitle(subtitles.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
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
                            if (movies.get(getAdapterPosition()).getOriginalTitle() != null) {
                                onMovieClickListener.onMovieClick(movies.get(getAdapterPosition())
                                        .getId(), movies.get(getAdapterPosition()).getOriginalTitle(), contentType);
                            } else {
                                onMovieClickListener.onMovieClick(movies.get(getAdapterPosition())
                                        .getId(), movies.get(getAdapterPosition()).getOriginalName(), contentType);
                            }
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
                            onMovieClickListener.onMovieClick(movies.get(getAdapterPosition())
                                    .getId(), movies.get(getAdapterPosition()).getOriginalTitle(), contentType);
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
                            onMovieClickListener.onMovieClick(movies.get(getAdapterPosition())
                                    .getId(), movies.get(getAdapterPosition()).getOriginalTitle(), contentType);
                        }
                    }
                });
            }
        }
    }

    private void getSubtitles() {
        subtitles = new ArrayList<>();
        if (genres != null) {
            for (int i = 0; i < movies.size(); i++) {
                StringBuilder genres = new StringBuilder();
                List<Integer> listGenres = movies.get(i).getGenreIds();
                for (int j = 0; j < listGenres.size(); j++) {
                    if (j < listGenres.size() - 1) {
                        genres.append(getGenreById(listGenres.get(j))).append(", ");
                    } else {
                        genres.append(getGenreById(listGenres.get(j))).append(" ");
                    }
                }
                if (getYear(movies.get(i).getReleaseDate()).length() < 3) {
                    subtitles.add(genres.toString());
                } else {
                    subtitles.add(getYear(movies.get(i).getReleaseDate()) + " " + genres);
                }
            }
        }
    }

    private String getYear(String year) {
        if (year != null && year.length() > 3) {
            return year.split("-")[0];
        }
        return "";
    }

    private String getGenreById(int genreId) {
        String genreStr = "";
        for (int i = 0; i < genres.size(); i++) {
            if (genreId == genres.get(i).getId()) {
                genreStr = genres.get(i).getName();
            }
        }
        return genreStr;
    }

}
