package com.best.movie.note.ui.movies;

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
import com.best.movie.note.adapters.MoviesCommonAdapter;
import com.best.movie.note.databinding.FragmentMoviesBinding;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL;
import static com.best.movie.note.utils.Constants.CARD_TYPE_HORIZONTAL_SMALL;
import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.SPAN_COUNT_HORIZONTAL_SMALL;

public class MoviesFragment extends Fragment implements MoviesCommonAdapter.OnMovieClickListener {

    private MoviesViewModel moviesViewModel;
    private FragmentMoviesBinding binding;
    private NavController navController;

    // Popular Movies
    private ArrayList<MovieResult> movieResults;
    private RecyclerView popularMoviesRecyclerView;
    private MoviesCommonAdapter moviesCommonAdapter;
    // Playing Now Movies
    private ArrayList<MovieResult> playingNowResults;
    private RecyclerView nowPlayingMoviesRecyclerView;
    private MoviesCommonAdapter nowPlayingMoviesCommonAdapter;
    // Trending Movies
    private ArrayList<MovieResult> trendingResults;
    private RecyclerView trendingRecyclerView;
    private MoviesCommonAdapter trendingMoviesCommonAdapter;
    // Top Rated Movies
    private ArrayList<MovieResult> topRatedResults;
    private RecyclerView topRatedRecyclerView;
    private MoviesCommonAdapter topRatedMoviesCommonAdapter;
    // Upcoming Movies
    private ArrayList<MovieResult> upcomingResults;
    private RecyclerView upcomingRecyclerView;
    private MoviesCommonAdapter upcomingMoviesCommonAdapter;
    // Genres Movies
    private ArrayList<GenreResult> genresResults;


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MoviesViewModel.class);

        navController = Navigation.findNavController(view);
        binding.setButtonHandler(new MoviesFragment.MoviesFragmentButtonsHandler());

        getGenres();
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
        moviesCommonAdapter = new MoviesCommonAdapter(
                movieResults, CARD_TYPE_VERTICAL, genresResults);
        popularMoviesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMoviesRecyclerView.setAdapter(moviesCommonAdapter);
        moviesCommonAdapter.setOnMovieClickListener(this);
        moviesCommonAdapter.notifyDataSetChanged();
    }

    private void fillPlayingNowRecyclerView() {
        nowPlayingMoviesRecyclerView = binding.playingInTheatresRecyclerView;
        nowPlayingMoviesCommonAdapter = new MoviesCommonAdapter(
                playingNowResults, CARD_TYPE_HORIZONTAL, genresResults);
        nowPlayingMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingMoviesRecyclerView.setAdapter(nowPlayingMoviesCommonAdapter);
        nowPlayingMoviesCommonAdapter.setOnMovieClickListener(this);
        nowPlayingMoviesCommonAdapter.notifyDataSetChanged();
    }

    private void fillTrendingRecyclerView() {
        trendingRecyclerView = binding.trendingRecyclerView;
        trendingMoviesCommonAdapter = new MoviesCommonAdapter(
                trendingResults, CARD_TYPE_VERTICAL, genresResults);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setAdapter(trendingMoviesCommonAdapter);
        trendingMoviesCommonAdapter.setOnMovieClickListener(this);
        trendingMoviesCommonAdapter.notifyDataSetChanged();
    }

    private void fillTopRatedRecyclerView() {
        topRatedRecyclerView = binding.topRatedRecyclerView;
        topRatedMoviesCommonAdapter = new MoviesCommonAdapter(
                topRatedResults, CARD_TYPE_HORIZONTAL_SMALL, genresResults);
        topRatedRecyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), SPAN_COUNT_HORIZONTAL_SMALL, GridLayoutManager.HORIZONTAL, false));
        topRatedRecyclerView.setAdapter(topRatedMoviesCommonAdapter);
        topRatedMoviesCommonAdapter.setOnMovieClickListener(this);
        topRatedMoviesCommonAdapter.notifyDataSetChanged();
    }

    private void fillUpcomingRecyclerView() {
        upcomingRecyclerView = binding.upComingRecyclerView;
        upcomingMoviesCommonAdapter = new MoviesCommonAdapter(
                upcomingResults, CARD_TYPE_VERTICAL, genresResults);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingRecyclerView.setAdapter(upcomingMoviesCommonAdapter);
        upcomingMoviesCommonAdapter.setOnMovieClickListener(this);
        upcomingMoviesCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int movieId, String originalName) {
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieId);
        bundle.putBoolean("is_movie", true);
        bundle.putString("original_name", originalName);
        navController.navigate(R.id.action_navigation_movies_to_mainMovieFragment, bundle);
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