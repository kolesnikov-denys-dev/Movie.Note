package com.best.movie.note.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.ItemSeasonHorizontalBinding;
import com.best.movie.note.model.response.tvshows.seasons.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder> {
    private List<Episode> episodes;

    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSeasonHorizontalBinding castCrewCircleItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_season_horizontal, parent, false);
        return new EpisodesViewHolder(castCrewCircleItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {
        holder.binding.setEpisodeResult(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class EpisodesViewHolder extends RecyclerView.ViewHolder {

        private ItemSeasonHorizontalBinding binding;

        public EpisodesViewHolder(@NonNull ItemSeasonHorizontalBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }
}