package com.best.movie.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.CastCrewCircleItemBinding;
import com.best.movie.note.model.movies.main.castcrew.Cast;

import java.util.List;


public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.MoviesViewHolder> {

    private List<Cast> castList;
    private OnCastClickListener onCastClickListener;

    public void setOnCastClickListener(OnCastClickListener onCastClickListener) {
        this.onCastClickListener = onCastClickListener;
    }

    public interface OnCastClickListener {
        void onCastClick(int castId, String originalName);
    }

    public CreditsAdapter(List<Cast> castList) {
        this.castList = castList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CastCrewCircleItemBinding castCrewCircleItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.cast_crew_circle_item, parent, false);
        return new MoviesViewHolder(castCrewCircleItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.binding.setCastResult(castList.get(position));
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        private CastCrewCircleItemBinding binding;

        public MoviesViewHolder(@NonNull CastCrewCircleItemBinding view) {
            super(view.getRoot());
            this.binding = view;
            binding.fullConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCastClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onCastClickListener.onCastClick(castList.get(getAdapterPosition()).getId(),
                                castList.get(getAdapterPosition()).getOriginalName());
                    }
                }
            });
        }
    }

}
