package com.best.movie.note.ui.list.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.best.movie.note.R;
import com.best.movie.note.adapter.ResultAdapter;
import com.best.movie.note.databinding.MoviesListFragmentBinding;
import com.best.movie.note.model.movies.cards.MovieResult;

public class MoviesListFragment extends Fragment {

    // Paging Library
    private MoviesListViewModel moviesListViewModel;
    private PagedList<MovieResult> results;
    private RecyclerView recyclerView;
    private ResultAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MoviesListFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String whatOpen = getArguments().getString("what_open");
            Toast.makeText(getContext(), whatOpen, Toast.LENGTH_SHORT).show();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(whatOpen);
        }

        moviesListViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MoviesListViewModel.class);

        getPopularMovies();

        swipeRefreshLayout = binding.swipeRefresh;
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getPopularMovies();
                    }
                });
    }

    public void getPopularMovies() {
        moviesListViewModel.getPagedListLiveData().observe(getActivity(),
                new Observer<PagedList<MovieResult>>() {
                    @Override
                    public void onChanged(PagedList<MovieResult> resultList) {
                        results = resultList;
                        fillRecyclerView();
                    }
                });
    }

    private void fillRecyclerView() {
        recyclerView = binding.moviesListRecyclerView;
        adapter = new ResultAdapter();
        adapter.submitList(results);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

}