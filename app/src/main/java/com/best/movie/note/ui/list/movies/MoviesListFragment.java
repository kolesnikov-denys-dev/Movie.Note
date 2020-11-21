package com.best.movie.note.ui.list.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.best.movie.note.R;

public class MoviesListFragment extends Fragment {

    private MoviesListViewModel mViewModel;

    public static MoviesListFragment newInstance() {
        return new MoviesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movies_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoviesListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String whatOpen = getArguments().getString("what_open");
            Toast.makeText(getContext(),whatOpen, Toast.LENGTH_SHORT).show();

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(whatOpen);
//            Util.log(" arg3:" + getArguments().getString("arg3")
//                    + " atr4:" + getArguments().getString("arg4")
//                    + " arg5:" + getArguments().getString("arg5")
//            );
        }

    }
}