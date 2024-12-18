package com.best.movie.note.ui.fragments.details.cast;

import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_MOVIE;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_TV_SHOW;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;

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
import com.best.movie.note.adapters.CommonContentAdapter;
import com.best.movie.note.databinding.FragmentCelebrityDetailsBinding;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.Cast;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;

import java.util.ArrayList;
import java.util.List;

public class CelebrityDetailsFragment extends Fragment implements CommonContentAdapter.OnMovieClickListener,
        CastsAdapter.OnCastClickListener {

    private CelebrityDetailsViewModel celebrityDetailsViewModel;
    private FragmentCelebrityDetailsBinding binding;
    private NavController navController;
    private CastDetailsApiResponse castDetailsResult;
    private MoviesCastApiResponse moviesCastResult;
    private RecyclerView moviesRecyclerView;
    private CommonContentAdapter moviesAdapter;
    private TvShowsCatApiResponse tvShowsCatResult;
    private RecyclerView tvShowsRecyclerView;
    private CommonContentAdapter tvShowsAdapter;
    private int castId;
    private boolean isMovie;
    private ArrayList<GenreResult> genresResults;
    private ArrayList<GenreResult> genresTvShowResults;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_celebrity_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        celebrityDetailsViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(CelebrityDetailsViewModel.class);

        navController = Navigation.findNavController(view);

        if (getArguments() != null) {
            castId = getArguments().getInt("cast_id");
            String castName = getArguments().getString("cast_name");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(castName);

            getCastDetails(castId, QUERY_LANGUAGE);
            getMoviesGenres();
            getTvShowsGenres();
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
                new Observer<TvShowsCatApiResponse>() {
                    @Override
                    public void onChanged(TvShowsCatApiResponse data) {
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
        if (moviesCastResult.getCast() != null) {
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
        moviesAdapter = new CommonContentAdapter(movies, CARD_TYPE_VERTICAL, genresResults, CONTENT_TYPE_MOVIE);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesRecyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieClickListener(this);
        moviesAdapter.notifyDataSetChanged();
    }

    private void fillTvShowsRecyclerView() {
        if (tvShowsCatResult.getCast() != null) {
            binding.setTvShowsList(true);
        }

        // Convert Cast to MovieResult
        ArrayList<MovieResult> movies = new ArrayList<>();
        for (int i = 0; i < tvShowsCatResult.getCast().size(); i++) {
            MovieResult newMovie = new MovieResult();
            com.best.movie.note.model.response.tvshows.details.cast.tvshows.Cast oldCast = tvShowsCatResult.getCast().get(i);
            newMovie.setOriginalTitle(oldCast.getOriginalName());
            newMovie.setId(oldCast.getId());
            newMovie.setPosterPath(oldCast.getPosterPath());
            newMovie.setGenreIds(oldCast.getGenreIds());
            movies.add(newMovie);
        }

        tvShowsRecyclerView = binding.tvshowsRecyclerView;
        tvShowsAdapter = new CommonContentAdapter(movies, CARD_TYPE_VERTICAL, genresTvShowResults, CONTENT_TYPE_TV_SHOW);
        tvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        tvShowsAdapter.setOnMovieClickListener(this);
        tvShowsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int contentId, String originalName, int contentType) {
        Bundle bundle = new Bundle();

        switch (contentType) {
            case CONTENT_TYPE_MOVIE: {
                Log.i("check", "was Clicked on :" + contentId);
                bundle.putInt("content_id", contentId);
                bundle.putInt("content_type", contentType);
                bundle.putString("original_name", originalName);
                navController.navigate(R.id.action_celebrityDetailsFragment_to_mainMovieFragment, bundle);
            }
            break;
            case CONTENT_TYPE_TV_SHOW: {
                bundle.putInt("content_id", contentId);
                bundle.putInt("content_type", contentType);
                bundle.putString("original_name", originalName);
                navController.navigate(R.id.action_celebrityDetailsFragment_to_mainMovieFragment, bundle);
            }
            break;
        }
    }

    @Override
    public void onCastClick(int castId, String originalName) {
        Toast.makeText(getContext(), "Implements", Toast.LENGTH_SHORT).show();
    }
}