package com.best.movie.note.ui.tvshows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.best.movie.note.R;

public class TvShowsFragment extends Fragment {

    private TvShowsViewModel TvShowsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TvShowsViewModel = new ViewModelProvider(this).get(TvShowsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tv_shows, container, false);
        TvShowsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}