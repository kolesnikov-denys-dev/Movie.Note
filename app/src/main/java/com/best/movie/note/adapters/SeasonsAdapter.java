package com.best.movie.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.CastCircleItemBinding;
import com.best.movie.note.databinding.SeasonVerticalItemBinding;
import com.best.movie.note.model.response.movies.cast.Cast;
import com.best.movie.note.model.response.tvshows.details.Genre;
import com.best.movie.note.model.response.tvshows.details.Season;

import java.util.ArrayList;
import java.util.List;


public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.MoviesViewHolder> {

    private List<Season> list;
    private OnSeasonClickListener onSeasonClickListener;

    public void setOnSeasonClickListener(OnSeasonClickListener onSeasonClickListener) {
        this.onSeasonClickListener = onSeasonClickListener;
    }

    public interface OnSeasonClickListener {
        void onSeasonClick(int castId, String originalName);
    }

    public SeasonsAdapter(ArrayList<Season> seasons) {
        this.list = seasons;

    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SeasonVerticalItemBinding seasonVerticalItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.season_vertical_item, parent, false);
        return new MoviesViewHolder(seasonVerticalItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.binding.setSeasonResult(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        private SeasonVerticalItemBinding binding;

        public MoviesViewHolder(@NonNull SeasonVerticalItemBinding view) {
            super(view.getRoot());
            this.binding = view;
            binding.fullConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeasonClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onSeasonClickListener.onSeasonClick(list.get(getAdapterPosition()).getId(),
                                list.get(getAdapterPosition()).getName());
                    }
                }
            });
        }
    }

}
