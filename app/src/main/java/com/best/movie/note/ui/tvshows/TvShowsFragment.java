package com.best.movie.note.ui.tvshows;

import android.os.Bundle;
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
import com.best.movie.note.adapters.CommonContentAdapter;
import com.best.movie.note.databinding.FragmentTvShowsBinding;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL;
import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL_SMALL;
import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_TV_SHOW;
import static com.best.movie.note.utils.Constants.SPAN_COUNT_HORIZONTAL_SMALL;

public class TvShowsFragment extends Fragment implements CommonContentAdapter.OnMovieClickListener {

    private TvShowsViewModel moviesViewModel;
    private FragmentTvShowsBinding binding;
    private NavController navController;

    // Airing Today
    private ArrayList<MovieResult> airingTodayResults;
    private RecyclerView airingTodayRecyclerView;
    private CommonContentAdapter airingTodayCommonAdapter;
    // Trending
    private ArrayList<MovieResult> trendingResults;
    private RecyclerView trendingRecyclerView;
    private CommonContentAdapter trendingCommonAdapter;
    // Top Rated
    private ArrayList<MovieResult> topRatedResult;
    private RecyclerView topRatedRecyclerView;
    private CommonContentAdapter topRatedCommonAdapter;
    // Popular
    private ArrayList<MovieResult> popularResults;
    private RecyclerView popularRecyclerView;
    private CommonContentAdapter popularCommonAdapter;
    // Genres Movies
    private ArrayList<GenreResult> genresResults;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_shows, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(TvShowsViewModel.class);

        navController = Navigation.findNavController(view);
        binding.setButtonHandler(new TvShowsFragment.MoviesFragmentButtonsHandler());

        getGenres();
    }

    private void getGenres() {
        moviesViewModel.getGenresTvShowsData().observe(getActivity(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        genresResults = (ArrayList<GenreResult>) data;
                        getAiringToday();
                        getTrending();
                        getTopRated();
                        getPopular();
                    }
                });
    }

    public void getAiringToday() {
        moviesViewModel.getAiringTodayData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        airingTodayResults = (ArrayList<MovieResult>) data;
                        fillAiringTodayRecyclerView();
                    }
                });
    }

    public void getTrending() {
        moviesViewModel.getTrendingData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        trendingResults = (ArrayList<MovieResult>) data;
                        fillTrendingRecyclerView();
                    }
                });
    }

    public void getTopRated() {
        moviesViewModel.getTopRatedData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        topRatedResult = (ArrayList<MovieResult>) data;
                        fillTopRatedRecyclerView();
                    }
                });
    }

    public void getPopular() {
        moviesViewModel.getPopularData().observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        popularResults = (ArrayList<MovieResult>) data;
                        fillPopularRecyclerView();
                    }
                });
    }

    private void fillAiringTodayRecyclerView() {
        airingTodayRecyclerView = binding.airingTodayRecyclerView;
        airingTodayCommonAdapter = new CommonContentAdapter(
                airingTodayResults, CARD_TYPE_HORIZONTAL, genresResults, CONTENT_TYPE_TV_SHOW);
        airingTodayRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        airingTodayRecyclerView.setAdapter(airingTodayCommonAdapter);
        airingTodayCommonAdapter.setOnMovieClickListener(this);
        airingTodayCommonAdapter.notifyDataSetChanged();
    }

    private void fillTrendingRecyclerView() {
        trendingRecyclerView = binding.trendingRecyclerView;
        trendingCommonAdapter = new CommonContentAdapter(
                trendingResults, CARD_TYPE_VERTICAL, genresResults, CONTENT_TYPE_TV_SHOW);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setAdapter(trendingCommonAdapter);
        trendingCommonAdapter.setOnMovieClickListener(this);
        trendingCommonAdapter.notifyDataSetChanged();
    }

    private void fillTopRatedRecyclerView() {
        topRatedRecyclerView = binding.topRatedRecyclerView;
        topRatedCommonAdapter = new CommonContentAdapter(
                topRatedResult, CARD_TYPE_HORIZONTAL_SMALL, genresResults, CONTENT_TYPE_TV_SHOW);
        topRatedRecyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), SPAN_COUNT_HORIZONTAL_SMALL,
                GridLayoutManager.HORIZONTAL, false));
        topRatedRecyclerView.setAdapter(topRatedCommonAdapter);
        topRatedCommonAdapter.setOnMovieClickListener(this);
        topRatedCommonAdapter.notifyDataSetChanged();
    }

    private void fillPopularRecyclerView() {
        popularRecyclerView = binding.popularRecyclerView;
        popularCommonAdapter = new CommonContentAdapter(
                popularResults, CARD_TYPE_VERTICAL, genresResults, CONTENT_TYPE_TV_SHOW);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularRecyclerView.setAdapter(popularCommonAdapter);
        popularCommonAdapter.setOnMovieClickListener(this);
        popularCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int movieId, String originalName, int contentType) {
        Bundle bundle = new Bundle();
        bundle.putInt("content_id", movieId);
        bundle.putInt("content_type", contentType);
        bundle.putString("original_name", originalName);
        navController.navigate(R.id.action_navigation_tv_shows_to_mainMovieFragment, bundle);
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

    @Override
    public void onDestroy() {
        moviesViewModel.disposeDisposable();
        super.onDestroy();
    }
}