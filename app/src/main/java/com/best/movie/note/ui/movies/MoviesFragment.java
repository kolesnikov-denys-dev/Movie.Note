package com.best.movie.note.ui.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.CommonContentAdapter;
import com.best.movie.note.databinding.FragmentMoviesBinding;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL;
import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL_SMALL;
import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_MOVIE;
import static com.best.movie.note.utils.Constants.ERROR_INTERNET;
import static com.best.movie.note.utils.Constants.KEY_CONTENT_ID;
import static com.best.movie.note.utils.Constants.KEY_CONTENT_TYPE;
import static com.best.movie.note.utils.Constants.KEY_ORIGINAL_NAME;
import static com.best.movie.note.utils.Constants.KEY_WHAT_OPEN;
import static com.best.movie.note.utils.Constants.SPAN_COUNT_HORIZONTAL_SMALL;

public class MoviesFragment extends Fragment implements CommonContentAdapter.OnMovieClickListener {

    private MoviesViewModel moviesViewModel;
    private FragmentMoviesBinding binding;
    private NavController navController;
    private RecyclerView popularMoviesRecyclerView;
    private CommonContentAdapter commonContentAdapter;
    private RecyclerView nowPlayingMoviesRecyclerView;
    private CommonContentAdapter nowPlayingCommonContentAdapter;
    private RecyclerView trendingRecyclerView;
    private CommonContentAdapter trendingCommonContentAdapter;
    private RecyclerView topRatedRecyclerView;
    private CommonContentAdapter topRatedCommonContentAdapter;
    private ArrayList<MovieResult> upcomingResults;
    private RecyclerView upcomingRecyclerView;
    private CommonContentAdapter upcomingCommonContentAdapter;
    private ArrayList<GenreResult> genresResults;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.mobile_navigation);
        moviesViewModel = new ViewModelProvider(backStackEntry).get(MoviesViewModel.class);
        binding.setButtonHandler(new MoviesFragment.MoviesFragmentButtonsHandler());

        moviesViewModel.getErrors().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable != null) {
                    Toast.makeText(getContext(), ERROR_INTERNET, Toast.LENGTH_SHORT).show();
                    moviesViewModel.clearErrors();
                }
            }
        });

        moviesViewModel.getGenresMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        genresResults = (ArrayList<GenreResult>) data;
                    }
                });

        moviesViewModel.getPopularMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        fillPopularRecyclerView((ArrayList<MovieResult>) data);
                    }
                });

        moviesViewModel.getNowPlayingMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        fillPlayingNowRecyclerView((ArrayList<MovieResult>) data);
                    }
                });

        moviesViewModel.getTrendingMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        fillTrendingRecyclerView((ArrayList<MovieResult>) data);
                    }
                });

        moviesViewModel.getTopRatedMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        fillTopRatedRecyclerView((ArrayList<MovieResult>) data);
                    }
                });

        moviesViewModel.getUpcomingMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        upcomingResults = (ArrayList<MovieResult>) data;
                        fillUpcomingRecyclerView();
                    }
                });
    }

    private void fillPopularRecyclerView(ArrayList<MovieResult> movieResults) {
        popularMoviesRecyclerView = binding.popularRecyclerView;
        commonContentAdapter = new CommonContentAdapter(
                movieResults, CARD_TYPE_VERTICAL, genresResults, CONTENT_TYPE_MOVIE);
        popularMoviesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        popularMoviesRecyclerView.setAdapter(commonContentAdapter);
        commonContentAdapter.setOnMovieClickListener(this);
        commonContentAdapter.notifyDataSetChanged();
    }

    private void fillPlayingNowRecyclerView(ArrayList<MovieResult> playingNowResults) {
        nowPlayingMoviesRecyclerView = binding.playingInTheatresRecyclerView;
        nowPlayingCommonContentAdapter = new CommonContentAdapter(
                playingNowResults, CARD_TYPE_HORIZONTAL, genresResults, CONTENT_TYPE_MOVIE);
        nowPlayingMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        nowPlayingMoviesRecyclerView.setAdapter(nowPlayingCommonContentAdapter);
        nowPlayingCommonContentAdapter.setOnMovieClickListener(this);
        nowPlayingCommonContentAdapter.notifyDataSetChanged();
    }

    private void fillTrendingRecyclerView(ArrayList<MovieResult> trendingResults) {
        trendingRecyclerView = binding.trendingRecyclerView;
        trendingCommonContentAdapter = new CommonContentAdapter(
                trendingResults, CARD_TYPE_VERTICAL, genresResults, CONTENT_TYPE_MOVIE);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        trendingRecyclerView.setAdapter(trendingCommonContentAdapter);
        trendingCommonContentAdapter.setOnMovieClickListener(this);
        trendingCommonContentAdapter.notifyDataSetChanged();
    }

    private void fillTopRatedRecyclerView(ArrayList<MovieResult> topRatedResults) {
        topRatedRecyclerView = binding.topRatedRecyclerView;
        topRatedCommonContentAdapter = new CommonContentAdapter(
                topRatedResults, CARD_TYPE_HORIZONTAL_SMALL, genresResults, CONTENT_TYPE_MOVIE);
        topRatedRecyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), SPAN_COUNT_HORIZONTAL_SMALL, GridLayoutManager.HORIZONTAL, false));
        topRatedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topRatedRecyclerView.setAdapter(topRatedCommonContentAdapter);
        topRatedCommonContentAdapter.setOnMovieClickListener(this);
        topRatedCommonContentAdapter.notifyDataSetChanged();
    }

    private void fillUpcomingRecyclerView() {
        upcomingRecyclerView = binding.upComingRecyclerView;
        upcomingCommonContentAdapter = new CommonContentAdapter(
                upcomingResults, CARD_TYPE_VERTICAL, genresResults, CONTENT_TYPE_MOVIE);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        upcomingRecyclerView.setAdapter(upcomingCommonContentAdapter);
        upcomingCommonContentAdapter.setOnMovieClickListener(this);
        upcomingCommonContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int movieId, String originalName, int contentType) {
        if (contentType == CONTENT_TYPE_MOVIE) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_CONTENT_ID, movieId);
            bundle.putInt(KEY_CONTENT_TYPE, contentType);
            bundle.putString(KEY_ORIGINAL_NAME, originalName);
            navController.navigate(R.id.action_navigation_movies_to_mainMovieFragment, bundle);
        }
    }

    public class MoviesFragmentButtonsHandler {
        Bundle bundle = new Bundle();

        public void popularSeeAll(View view) {
            bundle.putString(KEY_WHAT_OPEN, getString(R.string.popular));
            navController.navigate(R.id.action_navigation_movies_to_navigation_movies_list, bundle);
        }

        public void nowPlayingSeeAll(View view) {
            bundle.putString(KEY_WHAT_OPEN, getString(R.string.playing_in_theathres));
            navController.navigate(R.id.action_navigation_movies_to_navigation_movies_list, bundle);
        }

        public void trendingSeeAll(View view) {
            bundle.putString(KEY_WHAT_OPEN, getString(R.string.trending));
            navController.navigate(R.id.navigation_movies_list, bundle);
        }

        public void topRatedSeeAll(View view) {
            bundle.putString(KEY_WHAT_OPEN, getString(R.string.top_rated));
            navController.navigate(R.id.navigation_movies_list, bundle);
        }

        public void upComingSeeAll(View view) {
            bundle.putString(KEY_WHAT_OPEN, getString(R.string.upcoming));
            navController.navigate(R.id.navigation_movies_list, bundle);
        }
    }

}