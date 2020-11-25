package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.CastCircleItemBinding;
import com.best.movie.note.model.response.movies.cast.Cast;

import java.util.List;


public class CastsAdapter extends RecyclerView.Adapter<CastsAdapter.MoviesViewHolder> {

    private List<Cast> casts;
    private OnCastClickListener onCastClickListener;

    public void setOnCastClickListener(OnCastClickListener onCastClickListener) {
        this.onCastClickListener = onCastClickListener;
    }

    public interface OnCastClickListener {
        void onCastClick(int castId, String originalName);
    }

    public CastsAdapter(List<Cast> casts) {
        this.casts = casts;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CastCircleItemBinding castCrewCircleItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.cast_circle_item, parent, false);
        return new MoviesViewHolder(castCrewCircleItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.binding.setCastResult(casts.get(position));
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        private CastCircleItemBinding binding;

        public MoviesViewHolder(@NonNull CastCircleItemBinding view) {
            super(view.getRoot());
            this.binding = view;
            binding.fullConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCastClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onCastClickListener.onCastClick(casts.get(getAdapterPosition()).getId(),
                                casts.get(getAdapterPosition()).getOriginalName());
                    }
                }
            });
        }
    }

}
