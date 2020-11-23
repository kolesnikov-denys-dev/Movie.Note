package com.best.movie.note.ui.movies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapter.MoviesAdapter;
import com.best.movie.note.databinding.FragmentMoviesBinding;
import com.best.movie.note.model.genres.GenreResult;
import com.best.movie.note.model.movies.cards.MovieResult;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private FragmentMoviesBinding binding;
    private NavController navController;

    // Popular Movies
    private ArrayList<MovieResult> movieResults;
    private RecyclerView popularMoviesRecyclerView;
    private MoviesAdapter moviesAdapter;
    // Playing Now Movies
    private ArrayList<MovieResult> playingNowResults;
    private RecyclerView nowPlayingMoviesRecyclerView;
    private MoviesAdapter nowPlayingMoviesAdapter;
    // Trending Movies
    private ArrayList<MovieResult> trendingResults;
    private RecyclerView trendingRecyclerView;
    private MoviesAdapter trendingMoviesAdapter;
    // Top Rated Movies
    private ArrayList<MovieResult> topRatedResults;
    private RecyclerView topRatedRecyclerView;
    private MoviesAdapter topRatedMoviesAdapter;
    // Upcoming Movies
    private ArrayList<MovieResult> upcomingResults;
    private RecyclerView upcomingRecyclerView;
    private MoviesAdapter upcomingMoviesAdapter;
    // Genres Movies
    private ArrayList<GenreResult> genresResults;

    private boolean firstOpen = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentMoviesBinding.inflate(inflater, container, false);
//        return binding.getRoot();

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
            return binding.getRoot();
        } else {
            firstOpen = true;
            return binding.getRoot();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MoviesViewModel.class);

        navController = Navigation.findNavController(view);
        binding.setButtonHandler(new MoviesFragment.MoviesFragmentButtonsHandler());

        if (!firstOpen) {
            getGenres();
        }
    }

    private void getGenres() {
        moviesViewModel.getGenresMoviesData().observe(getActivity(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        genresResults = (ArrayList<GenreResult>) data;
                        getPopularMovies();
                        getNowPlayingMovies();
                        getTrendingMovies();
                        getTopRatedMovies();
                        getUpcomingMovies();
                    }
                });
    }

    public void getPopularMovies() {
        moviesViewModel.getPopularMoviesData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        movieResults = (ArrayList<MovieResult>) data;
                        fillPopularRecyclerView();
                    }
                });
    }

    public void getNowPlayingMovies() {
        moviesViewModel.getNowPlayingMoviesData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        playingNowResults = (ArrayList<MovieResult>) data;
                        fillPlayingNowRecyclerView();
                    }
                });
    }

    public void getTrendingMovies() {
        moviesViewModel.getTrendingMoviesData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        trendingResults = (ArrayList<MovieResult>) data;
                        fillTrendingRecyclerView();
                    }
                });
    }

    public void getTopRatedMovies() {
        moviesViewModel.getTopRatedMoviesData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        topRatedResults = (ArrayList<MovieResult>) data;
                        fillTopRatedRecyclerView();
                    }
                });
    }

    public void getUpcomingMovies() {
        moviesViewModel.getUpcomingMoviesData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        upcomingResults = (ArrayList<MovieResult>) data;
                        fillUpcomingRecyclerView();
                    }
                });
    }

    private void fillPopularRecyclerView() {
        popularMoviesRecyclerView = binding.popularRecyclerView;
        moviesAdapter = new MoviesAdapter(movieResults, 99, genresResults);
        popularMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMoviesRecyclerView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    private void fillPlayingNowRecyclerView() {
        nowPlayingMoviesRecyclerView = binding.playingInTheatresRecyclerView;
        nowPlayingMoviesAdapter = new MoviesAdapter(playingNowResults, 1, genresResults);
        nowPlayingMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingMoviesRecyclerView.setAdapter(nowPlayingMoviesAdapter);
        nowPlayingMoviesAdapter.notifyDataSetChanged();
    }

    private void fillTrendingRecyclerView() {
        trendingRecyclerView = binding.trendingRecyclerView;
        trendingMoviesAdapter = new MoviesAdapter(trendingResults, 99, genresResults);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setAdapter(trendingMoviesAdapter);
        trendingMoviesAdapter.notifyDataSetChanged();
    }

    private void fillTopRatedRecyclerView() {
        topRatedRecyclerView = binding.topRatedRecyclerView;
        topRatedMoviesAdapter = new MoviesAdapter(topRatedResults, 2, genresResults);
        topRatedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.HORIZONTAL, false));
        topRatedRecyclerView.setAdapter(topRatedMoviesAdapter);
        topRatedMoviesAdapter.notifyDataSetChanged();
    }

    private void fillUpcomingRecyclerView() {
        upcomingRecyclerView = binding.upComingRecyclerView;
        upcomingMoviesAdapter = new MoviesAdapter(upcomingResults, 99, genresResults);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingRecyclerView.setAdapter(upcomingMoviesAdapter);
        upcomingMoviesAdapter.notifyDataSetChanged();
    }

    public class MoviesFragmentButtonsHandler {

        Bundle bundle = new Bundle();

        public void popularSeeAll(View view) {
            bundle.putString("what_open", getString(R.string.popular));
            navController.navigate(R.id.action_navigation_movies_to_navigation_movies_list, bundle);
        }

        public void nowPlayingSeeAll(View view) {
            bundle.putString("what_open", getString(R.string.playing_in_theathres));
            navController.navigate(R.id.action_navigation_movies_to_navigation_movies_list, bundle);
        }

        public void trendingSeeAll(View view) {
            bundle.putString("what_open", getString(R.string.trending));
            navController.navigate(R.id.navigation_movies_list, bundle);
        }

        public void topRatedSeeAll(View view) {
            bundle.putString("what_open", getString(R.string.top_rated));
            navController.navigate(R.id.navigation_movies_list, bundle);
        }

        public void upComingSeeAll(View view) {
            bundle.putString("what_open", getString(R.string.upcoming));
            navController.navigate(R.id.navigation_movies_list, bundle);
        }
    }

}