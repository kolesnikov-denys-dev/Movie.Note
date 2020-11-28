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
import com.best.movie.note.ui.common.details.movie.MovieDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CelebrityDetailsFragment extends Fragment implements MoviesCommonAdapter.OnMovieClickListener,
        CastsAdapter.OnCastClickListener, SeasonsAdapter.OnSeasonClickListener {

    private MovieDetailsViewModel movieDetailsViewModel;
    private CelebrityDetailsFragmentBinding binding;
    private NavController navController;

    // Movies Region
    private MovieDetailsApiResponse movieDetailsResult;
    private VideosApiResponse movieTrailersResult;
    private CastCrewApiResponse moviesCastCrewResult;
    private RecyclerView moviesCastCrewRecyclerView;
    private CastsAdapter moviesCastCrewAdapter;
    private ArrayList<MovieResult> moviesRecommendationsResult;
    private RecyclerView moviesRecommendationRecyclerView;
    private MoviesCommonAdapter moviesRecommendationAdapter;
    private ArrayList<MovieResult> moviesSimilarResult;
    private RecyclerView moviesSimilarRecyclerView;
    private MoviesCommonAdapter moviesSimilarAdapter;
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

        movieDetailsViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MovieDetailsViewModel.class);

        navController = Navigation.findNavController(view);

        if (getArguments() != null) {
            castId = getArguments().getInt("cast_id");
            String castName = getArguments().getString("cast_name");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(castName);

//            binding.setShowSeasons(false);
//            getMovieGenres();
//            getMovieDetail(movieId, "en-US");
//            getMovieVideos(movieId, "en-US");
//            getMovieRecommendations(movieId, "en-US");
//            getMovieSimilar(movieId, "en-US");
//            getMovieCredits(movieId, "en-US");

        }
    }

    // Movies Region

//    private void getMovieGenres() {
//        movieDetailsViewModel.getGenresMoviesData().observe(getActivity(),
//                new Observer<List<GenreResult>>() {
//                    @Override
//                    public void onChanged(List<GenreResult> data) {
//                        moviesGenresResults = (ArrayList<GenreResult>) data;
//                    }
//                });
//    }
//
//    public void getMovieDetail(int movieId, String language) {
//        movieDetailsViewModel.getMovieDetails(movieId, language).observe(getActivity(),
//                new Observer<MovieDetailsApiResponse>() {
//                    @Override
//                    public void onChanged(MovieDetailsApiResponse data) {
//                        binding.setMovieDetailsResult(data);
//                        movieDetailsResult = data;
//                    }
//                });
//    }
//
//    public void getMovieVideos(int movieId, String language) {
//        movieDetailsViewModel.getMovieVideos(movieId, language).observe(getActivity(),
//                new Observer<VideosApiResponse>() {
//                    @Override
//                    public void onChanged(VideosApiResponse data) {
//                        movieTrailersResult = data;
//                    }
//                });
//    }
//
//    public void getMovieCredits(int movieId, String language) {
//        movieDetailsViewModel.getCredits(movieId, language).observe(getActivity(),
//                new Observer<CastCrewApiResponse>() {
//                    @Override
//                    public void onChanged(CastCrewApiResponse data) {
//                        moviesCastCrewResult = data;
//                        if (moviesCastCrewResult.getCast().isEmpty()) {
//                            binding.setShowCastList(false);
//                        } else {
//                            fillCastCrewRecyclerView();
//                            binding.setShowCastList(true);
//                        }
//                    }
//                });
//    }
//
//    // End Movies Region
//
//
//    // Movies Region
//
//    private void fillCastCrewRecyclerView() {
//        moviesCastCrewRecyclerView = binding.castCrewRecyclerView;
//        moviesCastCrewAdapter = new CastsAdapter(moviesCastCrewResult.getCast());
//        moviesCastCrewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        moviesCastCrewRecyclerView.setAdapter(moviesCastCrewAdapter);
//        moviesCastCrewAdapter.setOnCastClickListener(this);
//        moviesCastCrewAdapter.notifyDataSetChanged();
//    }
//
//    private void fillRecommendationRecyclerView() {
//        moviesRecommendationRecyclerView = binding.recommendedRecyclerView;
//        moviesRecommendationAdapter = new MoviesCommonAdapter(moviesRecommendationsResult, 99, moviesGenresResults);
//        moviesRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        moviesRecommendationRecyclerView.setAdapter(moviesRecommendationAdapter);
//        moviesRecommendationAdapter.setOnMovieClickListener(this);
//        moviesRecommendationAdapter.notifyDataSetChanged();
//    }
//    // End Movies Region
//
//
//    // End Movies Region

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