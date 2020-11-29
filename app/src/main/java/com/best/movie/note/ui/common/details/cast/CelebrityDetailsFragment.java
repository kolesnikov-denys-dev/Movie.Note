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
import com.best.movie.note.adapters.SeasonsAdapter;
import com.best.movie.note.databinding.CelebrityDetailsFragmentBinding;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;
import com.best.movie.note.ui.common.details.movie.MovieDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;

public class CelebrityDetailsFragment extends Fragment implements MoviesCommonAdapter.OnMovieClickListener,
        CastsAdapter.OnCastClickListener, SeasonsAdapter.OnSeasonClickListener {

    private CelebrityDetailsViewModel celebrityDetailsViewModel;
    private CelebrityDetailsFragmentBinding binding;
    private NavController navController;

    private CastDetailsApiResponse castDetailsResult;

    private MoviesCastApiResponse moviesCastResult;
    private RecyclerView moviesRecyclerView;
    private MoviesCommonAdapter moviesAdapter;

    private TvShowsCatApiResponse tvShowsCatResult;
    private RecyclerView tvShowsRecyclerView;
    private MoviesCommonAdapter tvShowsAdapter;

    private int castId;
    private boolean isMovie;
    private ArrayList<GenreResult> moviesGenresResults;
    // End Region

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

        if (getArguments() != null) {
            castId = getArguments().getInt("cast_id");
            String castName = getArguments().getString("cast_name");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(castName);


            getCastDetails(castId, QUERY_LANGUAGE);


        }
    }

    // Movies Region

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

    public void getCastMovies(int castId, String language) {
        celebrityDetailsViewModel.getCastMovies(castId, language).observe(getActivity(),
                new Observer<MoviesCastApiResponse>() {
                    @Override
                    public void onChanged(MoviesCastApiResponse data) {
//                        binding.setMovieDetailsResult(data);
                        moviesCastResult = data;
                    }
                });
    }

    public void getCastTvShows(int castId, String language) {
        celebrityDetailsViewModel.getCastTvShows(castId, language).observe(getActivity(),
                new Observer<TvShowsCatApiResponse>() {
                    @Override
                    public void onChanged(TvShowsCatApiResponse data) {
//                        binding.setMovieDetailsResult(data);
                        tvShowsCatResult = data;
                    }
                });
    }

    private void fillCastMoviesRecyclerView() {
        moviesRecyclerView = binding.moviesRecyclerView;
        moviesAdapter = new CastsAdapter(moviesCastResult);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesRecyclerView.setAdapter(moviesAdapter);
//        moviesAdapter.setOnCastClickListener(this);
        moviesAdapter.notifyDataSetChanged();
    }

    private void fillTvShowsRecyclerView() {

        tvShowsRecyclerView = binding.tvshowsRecyclerView;
        tvShowsAdapter = new MoviesCommonAdapter(tvShowsCatResult, 99, moviesGenresResults);
        tvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        tvShowsRecyclerView.setAdapter(moviesRecommendationAdapter);
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

    @Override
    public void onSeasonClick(int castId, String originalName) {

    }

}