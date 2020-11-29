package com.best.movie.note.ui.common.details.cast;

import android.os.Bundle;
import android.util.Log;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.CastsAdapter;
import com.best.movie.note.adapters.MoviesCommonAdapter;
import com.best.movie.note.databinding.CelebrityDetailsFragmentBinding;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.Cast;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;

public class CelebrityDetailsFragment extends Fragment implements MoviesCommonAdapter.OnMovieClickListener,
        CastsAdapter.OnCastClickListener {

    private CelebrityDetailsViewModel celebrityDetailsViewModel;
    private CelebrityDetailsFragmentBinding binding;
    private NavController navController;
    private CastDetailsApiResponse castDetailsResult;
    private MoviesCastApiResponse moviesCastResult;
    private RecyclerView moviesRecyclerView;
    private MoviesCommonAdapter moviesAdapter;
    private MoviesCastApiResponse tvShowsCatResult;
    private RecyclerView tvShowsRecyclerView;
    private MoviesCommonAdapter tvShowsAdapter;
    private int castId;
    private boolean isMovie;
    private ArrayList<GenreResult> genresResults;
    private ArrayList<GenreResult> genresTvShowResults;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.celebrity_details_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        celebrityDetailsViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(CelebrityDetailsViewModel.class);

        navController = Navigation.findNavController(view);

//        getTvShowsGenres();

        if (getArguments() != null) {
            castId = getArguments().getInt("cast_id");
            String castName = getArguments().getString("cast_name");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(castName);

            getCastDetails(castId, QUERY_LANGUAGE);
            getMoviesGenres();
        }

    }


    private void getCastDetails(int castId, String language) {
        celebrityDetailsViewModel.getCastDetails(castId, language).observe(getActivity(),
                new Observer<CastDetailsApiResponse>() {
                    @Override
                    public void onChanged(CastDetailsApiResponse data) {
                        castDetailsResult = data;
                        binding.setCastDetailsResult(data);
                        if (castDetailsResult.getDeathday() != null) {
                            binding.setShowDeathDay(true);
                        } else {
                            binding.setShowDeathDay(false);
                        }


                    }
                });
    }

    public void getMovies(int castId, String language) {
        celebrityDetailsViewModel.getCastMovies(castId, language).observe(getActivity(),
                new Observer<MoviesCastApiResponse>() {
                    @Override
                    public void onChanged(MoviesCastApiResponse data) {
                        moviesCastResult = data;
                        fillCastMoviesRecyclerView();
                    }
                });
    }

    public void getTvShows(int castId, String language) {
        celebrityDetailsViewModel.getCastTvShows(castId, language).observe(getActivity(),
                new Observer<MoviesCastApiResponse>() {
                    @Override
                    public void onChanged(MoviesCastApiResponse data) {
                        tvShowsCatResult = data;
                        fillTvShowsRecyclerView();
                    }
                });
    }

    private void getMoviesGenres() {
        celebrityDetailsViewModel.getGenresMoviesData().observe(getActivity(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        genresResults = (ArrayList<GenreResult>) data;
                        getMovies(castId, QUERY_LANGUAGE);
                    }
                });
    }

    private void getTvShowsGenres() {
        celebrityDetailsViewModel.getTvShowsGenresMoviesData().observe(getActivity(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        genresTvShowResults = (ArrayList<GenreResult>) data;
                        getTvShows(castId, QUERY_LANGUAGE);
                    }
                });
    }

    private void fillCastMoviesRecyclerView() {
        if (moviesCastResult.getCast().size() >= 1) {
            binding.setShowMoviesList(true);
        }

        // Convert Cast to MovieResult
        ArrayList<MovieResult> movies = new ArrayList<>();
        for (int i = 0; i < moviesCastResult.getCast().size(); i++) {
            MovieResult newMovie = new MovieResult();
            Cast oldCast = moviesCastResult.getCast().get(i);
            newMovie.setOriginalTitle(oldCast.getOriginalTitle());
            newMovie.setId(oldCast.getId());
            newMovie.setPosterPath(oldCast.getPosterPath());
            newMovie.setGenreIds(oldCast.getGenreIds());
            movies.add(newMovie);
        }

        moviesRecyclerView = binding.moviesRecyclerView;
        moviesAdapter = new MoviesCommonAdapter(movies, CARD_TYPE_VERTICAL, genresResults);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesRecyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieClickListener(this);
        moviesAdapter.notifyDataSetChanged();
    }

    private void fillTvShowsRecyclerView() {
        if (tvShowsCatResult.getCast().size() >= 1) {
            binding.setTvShowsList(true);
        }

        // Convert Cast to MovieResult
        ArrayList<MovieResult> movies = new ArrayList<>();
        for (int i = 0; i < moviesCastResult.getCast().size(); i++) {
            MovieResult newMovie = new MovieResult();
            Cast oldCast = moviesCastResult.getCast().get(i);
            newMovie.setOriginalTitle(oldCast.getOriginalTitle());
            newMovie.setId(oldCast.getId());
            newMovie.setPosterPath(oldCast.getPosterPath());
            newMovie.setGenreIds(oldCast.getGenreIds());
            movies.add(newMovie);
        }


        tvShowsRecyclerView = binding.tvshowsRecyclerView;
        tvShowsAdapter = new MoviesCommonAdapter(tvShowsCatResult, CARD_TYPE_VERTICAL, genresTvShowResults);
        tvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        tvShowsAdapter.setOnMovieClickListener(this);
        tvShowsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int movieId, String originalName) {
        Log.i("check", "was Clicked on :" + movieId);
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieId);
        bundle.putBoolean("is_movie", isMovie);
        bundle.putString("original_name", originalName);
        navController.navigate(R.id.action_mainMovieFragment_self, bundle);
    }

    @Override
    public void onCastClick(int castId, String originalName) {
        Toast.makeText(getContext(), "Implements", Toast.LENGTH_SHORT).show();
    }

}