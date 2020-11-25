package com.best.movie.note.ui.celebrities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.best.movie.note.R;

public class CelebritiesFragment extends Fragment {

    private CelebritiesViewModel celebritiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        celebritiesViewModel = new ViewModelProvider(this).get(CelebritiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_celebrities, container, false);


        celebritiesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;
    }

}