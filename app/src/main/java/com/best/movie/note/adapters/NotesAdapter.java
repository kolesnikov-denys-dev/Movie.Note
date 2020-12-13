package com.best.movie.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.databinding.CastCircleItemBinding;
import com.best.movie.note.databinding.NoteItemBinding;
import com.best.movie.note.model.response.movies.cast.Cast;
import com.best.movie.note.ui.note.Note;

import java.util.ArrayList;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> list;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onNoteClickListener) {
        this.onClickListener = onNoteClickListener;
    }

    public interface OnClickListener {
        void onCastClick(int castId, String originalName, int contentType);
    }

    public NotesAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Note> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteItemBinding noteItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.note_item, parent, false);
        return new NotesViewHolder(noteItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.binding.setNoteResult(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        private NoteItemBinding binding;

        public NotesViewHolder(@NonNull NoteItemBinding view) {
            super(view.getRoot());
            this.binding = view;
            binding.cardViewFull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = list.get(getAdapterPosition());
                    if (onClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onClickListener.onCastClick((int) note.getIdContent(),
                                note.getName(),
                                (int) note.getContentType());
                    }
                }
            });
        }
    }

}
