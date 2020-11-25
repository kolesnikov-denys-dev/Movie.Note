package com.best.movie.note.ui.common.details;

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
import com.best.movie.note.databinding.MovieDetailsFragmentBinding;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsFragment extends Fragment implements MoviesCommonAdapter.OnMovieClickListener,
        CastsAdapter.OnCastClickListener {

    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsFragmentBinding binding;
    private NavController navController;

    // Movie Details
    private MovieDetailsApiResponse movieDetailsResult;
    // Trailers video
    private VideosApiResponse trailersResult;
    // Cast & Crew Movies
    private CastCrewApiResponse castCrewResult;
    private RecyclerView castCrewRecyclerView;
    private CastsAdapter castCrewAdapter;
    // Recommendation Movies
    private ArrayList<MovieResult> recommendationsResult;
    private RecyclerView recommendationRecyclerView;
    private MoviesCommonAdapter recommendationAdapter;
    // Similar Movies
    private ArrayList<MovieResult> similarResult;
    private RecyclerView similarRecyclerView;
    private MoviesCommonAdapter similarAdapter;
    private int movieId;
    // Genres Movies
    private ArrayList<GenreResult> genresResults;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false);
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
            String originalName = getArguments().getString("original_name");
            Toast.makeText(getContext(), "Movie Id: " + movieId, Toast.LENGTH_SHORT).show();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(originalName);

            getGenres();
            getMovieDetail(movieId, "en-US");
            getMovieVideos(movieId, "en-US");
            getRecommendations(movieId, "en-US");
            getSimilar(movieId, "en-US");
            getCredits(movieId, "en-US");
        }
    }

    private void getGenres() {
        movieDetailsViewModel.getGenresMoviesData().observe(getActivity(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        genresResults = (ArrayList<GenreResult>) data;
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
                        trailersResult = data;
                    }
                });
    }

    public void getCredits(int movieId, String language) {
        movieDetailsViewModel.getCredits(movieId, language).observe(getActivity(),
                new Observer<CastCrewApiResponse>() {
                    @Override
                    public void onChanged(CastCrewApiResponse data) {
                        castCrewResult = data;
                        if (castCrewResult.getCast().isEmpty()) {
                            binding.setShowCastList(false);
                        } else {
                            fillCastCrewRecyclerView();
                            binding.setShowCastList(true);
                        }
                    }
                });
    }

    public void getRecommendations(int movieId, String language) {
        movieDetailsViewModel.getRecommendations(movieId, language).observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        recommendationsResult = (ArrayList<MovieResult>) data;
                        if (recommendationsResult.isEmpty()) {
                            binding.setShowRecommendationList(false);
                        } else {
                            binding.setShowRecommendationList(true);
                            fillRecommendationRecyclerView();
                        }
                    }
                });
    }

    public void getSimilar(int movieId, String language) {
        movieDetailsViewModel.getSimilar(movieId, language).observe(getActivity(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        similarResult = (ArrayList<MovieResult>) data;
                        if (similarResult.isEmpty()) {
                            binding.setShowSimilarList(false);
                        } else {
                            binding.setShowSimilarList(true);
                            fillSimilarRecyclerView();
                        }
                    }
                });
    }

    private void fillCastCrewRecyclerView() {
        castCrewRecyclerView = binding.castCrewRecyclerView;
        castCrewAdapter = new CastsAdapter(castCrewResult.getCast());
        castCrewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        castCrewRecyclerView.setAdapter(castCrewAdapter);
        castCrewAdapter.setOnCastClickListener(this);
        castCrewAdapter.notifyDataSetChanged();
    }

    private void fillRecommendationRecyclerView() {
        recommendationRecyclerView = binding.recommendedRecyclerView;
        recommendationAdapter = new MoviesCommonAdapter(recommendationsResult, 99, genresResults);
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendationRecyclerView.setAdapter(recommendationAdapter);
        recommendationAdapter.setOnMovieClickListener(this);
        recommendationAdapter.notifyDataSetChanged();
    }

    private void fillSimilarRecyclerView() {
        similarRecyclerView = binding.similarRecyclerView;
        similarAdapter = new MoviesCommonAdapter(similarResult, 99, genresResults);
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        similarRecyclerView.setAdapter(similarAdapter);
        similarAdapter.setOnMovieClickListener(this);
        similarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int movieId, String originalName) {
        Log.i("check", "was Clicked on :" + movieId);
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieId);
        bundle.putString("original_name", originalName);
        navController.navigate(R.id.action_mainMovieFragment_self, bundle);
    }

    @Override
    public void onCastClick(int castId, String originalName) {
        Toast.makeText(getContext(), "Implements", Toast.LENGTH_SHORT).show();
    }

    public class MovieDetailsFragmentButtonsHandler {
        //TODO if not trailer
        public void showTrailer(View view) {
            if (trailersResult != null &&
                    trailersResult.getVideosResults() != null &&
                    trailersResult.getVideosResults().get(0) != null &&
                    trailersResult.getVideosResults().get(0).getKey() != null) {
                String id = trailersResult.getVideosResults().get(0).getKey();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
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