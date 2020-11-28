package com.best.movie.note.ui.common.details.cast;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import com.best.movie.note.databinding.MovieDetailsFragmentBinding;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.Season;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;
import com.best.movie.note.ui.common.details.movie.MovieDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;

public class CelebrityDetailsFragment extends Fragment implements MoviesCommonAdapter.OnMovieClickListener,
        CastsAdapter.OnCastClickListener, SeasonsAdapter.OnSeasonClickListener {

    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsFragmentBinding binding;
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
    private int movieId;
    private boolean isMovie;
    private ArrayList<GenreResult> moviesGenresResults;
    // End Region

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.celebrity_details_fragment, container, false);
        binding.setButtonHandler(new MovieDetailsFragmentButtonsHandler());
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
            movieId = getArguments().getInt("movie_id");
            isMovie = getArguments().getBoolean("is_movie");
            String originalName = getArguments().getString("original_name");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(originalName);
            if (isMovie) {
                binding.setShowSeasons(false);
                getMovieGenres();
                getMovieDetail(movieId, "en-US");
                getMovieVideos(movieId, "en-US");
                getMovieRecommendations(movieId, "en-US");
                getMovieSimilar(movieId, "en-US");
                getMovieCredits(movieId, "en-US");
            }
        }
    }

    // Movies Region

    private void getMovieGenres() {
        movieDetailsViewModel.getGenresMoviesData().observe(getActivity(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        moviesGenresResults = (ArrayList<GenreResult>) data;
                    }
                });
    }

    public void getMovieDetail(int movieId, String language) {
        movieDetailsViewModel.getMovieDetails(movieId, language).observe(getActivity(),
                new Observer<MovieDetailsApiResponse>() {
                    @Override
                    public void onChanged(MovieDetailsApiResponse data) {
                        binding.setMovieDetailsResult(data);
                        movieDetailsResult = data;
                    }
                });
    }

    public void getMovieVideos(int movieId, String language) {
        movieDetailsViewModel.getMovieVideos(movieId, language).observe(getActivity(),
                new Observer<VideosApiResponse>() {
                    @Override
                    public void onChanged(VideosApiResponse data) {
                        movieTrailersResult = data;
                    }
                });
    }

    public void getMovieCredits(int movieId, String language) {
        movieDetailsViewModel.getCredits(movieId, language).observe(getActivity(),
                new Observer<CastCrewApiResponse>() {
                    @Override
                    public void onChanged(CastCrewApiResponse data) {
                        moviesCastCrewResult = data;
                        if (moviesCastCrewResult.getCast().isEmpty()) {
                            binding.setShowCastList(false);
                        } else {
                            fillCastCrewRecyclerView();
                            binding.setShowCastList(true);
                        }
                    }
                });
    }

    public void getMovieRecommendations(int movieId, String language) {
        movieDetailsViewModel.getRecommendations(movieId, language).observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        moviesRecommendationsResult = (ArrayList<MovieResult>) data;
                        if (moviesRecommendationsResult.isEmpty()) {
                            binding.setShowRecommendationList(false);
                        } else {
                            binding.setShowRecommendationList(true);
                            fillRecommendationRecyclerView();
                        }
                    }
                });
    }

    public void getMovieSimilar(int movieId, String language) {
        movieDetailsViewModel.getSimilar(movieId, language).observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        moviesSimilarResult = (ArrayList<MovieResult>) data;
                        if (moviesSimilarResult.isEmpty()) {
                            binding.setShowSimilarList(false);
                        } else {
                            binding.setShowSimilarList(true);
                            fillSimilarRecyclerView();
                        }
                    }
                });
    }

    // End Movies Region



    // Movies Region

    private void fillCastCrewRecyclerView() {
        moviesCastCrewRecyclerView = binding.castCrewRecyclerView;
        moviesCastCrewAdapter = new CastsAdapter(moviesCastCrewResult.getCast());
        moviesCastCrewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesCastCrewRecyclerView.setAdapter(moviesCastCrewAdapter);
        moviesCastCrewAdapter.setOnCastClickListener(this);
        moviesCastCrewAdapter.notifyDataSetChanged();
    }

    private void fillRecommendationRecyclerView() {
        moviesRecommendationRecyclerView = binding.recommendedRecyclerView;
        moviesRecommendationAdapter = new MoviesCommonAdapter(moviesRecommendationsResult, 99, moviesGenresResults);
        moviesRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesRecommendationRecyclerView.setAdapter(moviesRecommendationAdapter);
        moviesRecommendationAdapter.setOnMovieClickListener(this);
        moviesRecommendationAdapter.notifyDataSetChanged();
    }

    private void fillSimilarRecyclerView() {
        moviesSimilarRecyclerView = binding.similarRecyclerView;
        moviesSimilarAdapter = new MoviesCommonAdapter(moviesSimilarResult, 99, moviesGenresResults);
        moviesSimilarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesSimilarRecyclerView.setAdapter(moviesSimilarAdapter);
        moviesSimilarAdapter.setOnMovieClickListener(this);
        moviesSimilarAdapter.notifyDataSetChanged();
    }

    // End Movies Region



    // End Movies Region

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

    public class MovieDetailsFragmentButtonsHandler {
        public void showTrailer(View view) {
            if (movieTrailersResult != null &&
                    movieTrailersResult.getVideosResults() != null &&
                    movieTrailersResult.getVideosResults().get(0) != null &&
                    movieTrailersResult.getVideosResults().get(0).getKey() != null) {
                String id = movieTrailersResult.getVideosResults().get(0).getKey();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            } else if (tvShowsTrailersResult != null &&
                    tvShowsTrailersResult.getVideosResults() != null &&
                    tvShowsTrailersResult.getVideosResults().get(0) != null &&
                    tvShowsTrailersResult.getVideosResults().get(0).getKey() != null) {
                {
                    String id = tvShowsTrailersResult.getVideosResults().get(0).getKey();
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
                    try {
                        startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        startActivity(webIntent);
                    }
                }
            } else {
                Toast.makeText(getContext(), "Not trailer", Toast.LENGTH_SHORT).show();
            }
        }

        public void share(View view) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            String message = "Create notes your movies with the application \"Movie Note\":";
            message += "\n\n" + movieDetailsResult.getOriginalTitle();
            message += "\n\n " + movieDetailsResult.getOverview();

            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

}