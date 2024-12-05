package com.best.movie.note.ui.fragments.details.movie;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.best.movie.note.adapters.SeasonsAdapter;
import com.best.movie.note.databinding.FragmentMovieDetailsBinding;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.Season;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_MOVIE;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_TV_SHOW;
import static com.best.movie.note.utils.Constants.ERROR_NOT_TRAILER;
import static com.best.movie.note.utils.Constants.KEY_CAST_ID;
import static com.best.movie.note.utils.Constants.KEY_CAST_NAME;
import static com.best.movie.note.utils.Constants.KEY_CONTENT_ID;
import static com.best.movie.note.utils.Constants.KEY_CONTENT_TYPE;
import static com.best.movie.note.utils.Constants.KEY_GENRES;
import static com.best.movie.note.utils.Constants.KEY_ORIGINAL_NAME;
import static com.best.movie.note.utils.Constants.KEY_POSTER_PATH;
import static com.best.movie.note.utils.Constants.KEY_SEASON_NUMBER;
import static com.best.movie.note.utils.Constants.KEY_TV_ID;
import static com.best.movie.note.utils.Constants.LANGUAGE_EN;

public class MovieDetailsFragment extends Fragment implements CommonContentAdapter.OnMovieClickListener,
        CastsAdapter.OnCastClickListener, SeasonsAdapter.OnSeasonClickListener {

    private MovieDetailsViewModel movieDetailsViewModel;
    private FragmentMovieDetailsBinding binding;
    private NavController navController;

    // Movies Region
    private MovieDetailsApiResponse movieDetailsResult;
    private VideosApiResponse movieTrailersResult;
    private CastCrewApiResponse moviesCastCrewResult;
    private RecyclerView moviesCastCrewRecyclerView;
    private CastsAdapter moviesCastCrewAdapter;
    private ArrayList<MovieResult> moviesRecommendationsResult;
    private RecyclerView moviesRecommendationRecyclerView;
    private CommonContentAdapter moviesRecommendationAdapter;
    private ArrayList<MovieResult> moviesSimilarResult;
    private RecyclerView moviesSimilarRecyclerView;
    private CommonContentAdapter moviesSimilarAdapter;
    private int contentId;
    private int contentType;
    private ArrayList<GenreResult> moviesGenresResults;
    // End Region

    // Tv Shows
    private TvShowsApiResponse tvShowsDetailsResult;
    private VideosApiResponse tvShowsTrailersResult;
    private CastCrewApiResponse tvShowsCastCrewResult;
    private RecyclerView tvShowsCastCrewRecyclerView;
    private CastsAdapter tvShowsCastCrewAdapter;
    private ArrayList<MovieResult> tvShowsRecommendationsResult;
    private RecyclerView tvShowsRecommendationRecyclerView;
    private CommonContentAdapter tvShowsRecommendationAdapter;
    private ArrayList<MovieResult> tvShowsSimilarResult;
    private RecyclerView tvShowsSimilarRecyclerView;
    private CommonContentAdapter tvShowsSimilarAdapter;
    private ArrayList<GenreResult> tvShowsGenresResults;
    private RecyclerView tvShowsSeasonsRecyclerView;
    private SeasonsAdapter tvShowsSeasonsAdapter;
    private ArrayList<Season> tvShowsSeasonsResults;
    // End Region

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        binding.setButtonHandler(new MovieDetailsFragmentButtonsHandler());
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            binding.setAddNoteShow(true);
        } else {
            binding.setAddNoteShow(false);
        }

        movieDetailsViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MovieDetailsViewModel.class);

//        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.mobile_navigation);
//        movieDetailsViewModel = new ViewModelProvider(backStackEntry).get(MovieDetailsViewModel.class);

        if (getArguments() != null) {
            contentId = getArguments().getInt(KEY_CONTENT_ID);
            contentType = getArguments().getInt(KEY_CONTENT_TYPE);
            String originalName = getArguments().getString(KEY_ORIGINAL_NAME);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(originalName);

            switch (contentType) {
                case CONTENT_TYPE_MOVIE: {
                    binding.setShowSeasons(false);
                    getMovieGenres();
                    getMovieDetail(contentId, LANGUAGE_EN);
                    getMovieVideos(contentId, LANGUAGE_EN);
                    getMovieRecommendations(contentId, LANGUAGE_EN);
                    getMovieSimilar(contentId, LANGUAGE_EN);
                    getMovieCredits(contentId, LANGUAGE_EN);
                }
                break;
                case CONTENT_TYPE_TV_SHOW: {
                    binding.setShowSeasons(true);
                    getTvShowsGenres();
                    getTvShowsDetail(contentId, LANGUAGE_EN);
                    getTvShowsVideos(contentId, LANGUAGE_EN);
                    getTvShowsCredits(contentId, LANGUAGE_EN);
                    getTvShowsRecommendations(contentId, LANGUAGE_EN);
                    getTvShowsSimilar(contentId, LANGUAGE_EN);
                }
                break;
            }
        }
    }

    // Movies Region
    private void getMovieGenres() {
        movieDetailsViewModel.updateGenresMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        moviesGenresResults = (ArrayList<GenreResult>) data;
                    }
                });
    }

    public void getMovieDetail(int movieId, String language) {
        movieDetailsViewModel.updateMovieDetails(movieId, language).observe(getViewLifecycleOwner(),
                new Observer<MovieDetailsApiResponse>() {
                    @Override
                    public void onChanged(MovieDetailsApiResponse data) {
                        binding.setMovieDetailsResult(data);
                        movieDetailsResult = data;
                    }
                });
    }

    public void getMovieVideos(int movieId, String language) {
        movieDetailsViewModel.updateMovieVideos(movieId, language).observe(getViewLifecycleOwner(),
                new Observer<VideosApiResponse>() {
                    @Override
                    public void onChanged(VideosApiResponse data) {
                        movieTrailersResult = data;
                    }
                });
    }

    public void getMovieCredits(int movieId, String language) {
        movieDetailsViewModel.updateCredits(movieId, language).observe(getViewLifecycleOwner(),
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
        movieDetailsViewModel.updateRecommendations(movieId, language).observe(getViewLifecycleOwner(),
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
        movieDetailsViewModel.updateSimilar(movieId, language).observe(getViewLifecycleOwner(),
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

    // Tv Shows Region
    private void getTvShowsGenres() {
        movieDetailsViewModel.getTvShowsGenresMoviesData().observe(getViewLifecycleOwner(),
                new Observer<List<GenreResult>>() {
                    @Override
                    public void onChanged(List<GenreResult> data) {
                        tvShowsGenresResults = (ArrayList<GenreResult>) data;
                    }
                });
    }

    public void getTvShowsDetail(int movieId, String language) {
        movieDetailsViewModel.getTvShowsDetails(movieId, language).observe(getViewLifecycleOwner(),
                new Observer<TvShowsApiResponse>() {
                    @Override
                    public void onChanged(TvShowsApiResponse data) {
                        binding.setTvShowDetailsResult(data);
                        tvShowsDetailsResult = data;
                        tvShowsSeasonsResults = (ArrayList<Season>) tvShowsDetailsResult.getSeasons();
                        fillTvShowsSeasonsRecyclerView();
                    }
                });
    }

    public void getTvShowsVideos(int movieId, String language) {
        movieDetailsViewModel.getTvShowsVideos(movieId, language).observe(getViewLifecycleOwner(),
                new Observer<VideosApiResponse>() {
                    @Override
                    public void onChanged(VideosApiResponse data) {
                        tvShowsTrailersResult = data;
                    }
                });
    }

    public void getTvShowsCredits(int movieId, String language) {
        movieDetailsViewModel.getTvShowsCredits(movieId, language).observe(getViewLifecycleOwner(),
                new Observer<CastCrewApiResponse>() {
                    @Override
                    public void onChanged(CastCrewApiResponse data) {
                        tvShowsCastCrewResult = data;
                        if (tvShowsCastCrewResult.getCast().isEmpty()) {
                            binding.setShowCastList(false);
                        } else {
                            fillTvShowsCastCrewRecyclerView();
                            binding.setShowCastList(true);
                        }
                    }
                });
    }

    public void getTvShowsRecommendations(int movieId, String language) {
        movieDetailsViewModel.getTvShowsRecommendations(movieId, language).observe(getViewLifecycleOwner(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        tvShowsRecommendationsResult = (ArrayList<MovieResult>) data;
                        if (tvShowsRecommendationsResult.isEmpty()) {
                            binding.setShowRecommendationList(false);
                        } else {
                            binding.setShowRecommendationList(true);
                            fillTvShowsRecommendationRecyclerView();
                        }
                    }
                });
    }

    public void getTvShowsSimilar(int movieId, String language) {
        movieDetailsViewModel.getTvShowsSimilar(movieId, language).observe(getViewLifecycleOwner(),
                new Observer<List<MovieResult>>() {
                    @Override
                    public void onChanged(List<MovieResult> data) {
                        tvShowsSimilarResult = (ArrayList<MovieResult>) data;
                        if (tvShowsSimilarResult.isEmpty()) {
                            binding.setShowSimilarList(false);
                        } else {
                            binding.setShowSimilarList(true);
                            fillTvShowsSimilarRecyclerView();
                        }
                    }
                });
    }

    // End Tv Shows Region


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
        moviesRecommendationAdapter = new CommonContentAdapter(moviesRecommendationsResult, CARD_TYPE_VERTICAL, moviesGenresResults, CONTENT_TYPE_MOVIE);
        moviesRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesRecommendationRecyclerView.setAdapter(moviesRecommendationAdapter);
        moviesRecommendationAdapter.setOnMovieClickListener(this);
        moviesRecommendationAdapter.notifyDataSetChanged();
    }

    private void fillSimilarRecyclerView() {
        moviesSimilarRecyclerView = binding.similarRecyclerView;
        moviesSimilarAdapter = new CommonContentAdapter(moviesSimilarResult, CARD_TYPE_VERTICAL, moviesGenresResults, CONTENT_TYPE_MOVIE);
        moviesSimilarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        moviesSimilarRecyclerView.setAdapter(moviesSimilarAdapter);
        moviesSimilarAdapter.setOnMovieClickListener(this);
        moviesSimilarAdapter.notifyDataSetChanged();
    }

    // End Movies Region


    // Tv Shows Region

    private void fillTvShowsCastCrewRecyclerView() {
        tvShowsCastCrewRecyclerView = binding.castCrewRecyclerView;
        tvShowsCastCrewAdapter = new CastsAdapter(tvShowsCastCrewResult.getCast());
        tvShowsCastCrewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvShowsCastCrewRecyclerView.setAdapter(tvShowsCastCrewAdapter);
        tvShowsCastCrewAdapter.setOnCastClickListener(this);
        tvShowsCastCrewAdapter.notifyDataSetChanged();
    }

    private void fillTvShowsRecommendationRecyclerView() {
        tvShowsRecommendationRecyclerView = binding.recommendedRecyclerView;
        tvShowsRecommendationAdapter = new CommonContentAdapter(tvShowsRecommendationsResult, CARD_TYPE_VERTICAL, tvShowsGenresResults, CONTENT_TYPE_TV_SHOW);
        tvShowsRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvShowsRecommendationRecyclerView.setAdapter(tvShowsRecommendationAdapter);
        tvShowsRecommendationAdapter.setOnMovieClickListener(this);
        tvShowsRecommendationAdapter.notifyDataSetChanged();
    }

    private void fillTvShowsSimilarRecyclerView() {
        tvShowsSimilarRecyclerView = binding.similarRecyclerView;
        tvShowsSimilarAdapter = new CommonContentAdapter(tvShowsSimilarResult, CARD_TYPE_VERTICAL, tvShowsGenresResults, CONTENT_TYPE_TV_SHOW);
        tvShowsSimilarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvShowsSimilarRecyclerView.setAdapter(tvShowsSimilarAdapter);
        tvShowsSimilarAdapter.setOnMovieClickListener(this);
        tvShowsSimilarAdapter.notifyDataSetChanged();
    }

    private void fillTvShowsSeasonsRecyclerView() {
        tvShowsSeasonsRecyclerView = binding.seasonsRecyclerView;
        tvShowsSeasonsAdapter = new SeasonsAdapter(tvShowsSeasonsResults);
        tvShowsSeasonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvShowsSeasonsRecyclerView.setAdapter(tvShowsSeasonsAdapter);
        tvShowsSeasonsAdapter.setOnSeasonClickListener(this);
        tvShowsSeasonsAdapter.notifyDataSetChanged();
    }

    // End Movies Region

    @Override
    public void onMovieClick(int movieId, String originalName, int contentType) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CONTENT_ID, movieId);
        bundle.putInt(KEY_CONTENT_TYPE, contentType);
        bundle.putString(KEY_ORIGINAL_NAME, originalName);
        navController.navigate(R.id.action_mainMovieFragment_self, bundle);
    }

    @Override
    public void onCastClick(int castId, String castName) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CAST_ID, castId);
        bundle.putString(KEY_CAST_NAME, castName);
        navController.navigate(R.id.action_mainMovieFragment_to_celebrityDetailsFragment, bundle);
    }

    @Override
    public void onSeasonClick(int tvId, String originalName, int season) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TV_ID, contentId);
        bundle.putInt(KEY_SEASON_NUMBER, season);
        bundle.putString(KEY_ORIGINAL_NAME, originalName);
        navController.navigate(R.id.action_mainMovieFragment_to_seasonDetailsFragment, bundle);
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
                Toast.makeText(getContext(), ERROR_NOT_TRAILER, Toast.LENGTH_SHORT).show();
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

        public void myNote(View view) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_CONTENT_ID, contentId);
            bundle.putInt(KEY_CONTENT_TYPE, contentType);
            bundle.putString(KEY_ORIGINAL_NAME, getArguments().getString(KEY_ORIGINAL_NAME));
            bundle.putString(KEY_GENRES, binding.genresTextView.getText().toString());
            if (contentType == CONTENT_TYPE_MOVIE) {
                bundle.putString(KEY_POSTER_PATH, movieDetailsResult.getPosterPath());
            } else if (contentType == CONTENT_TYPE_TV_SHOW) {
                bundle.putString(KEY_POSTER_PATH, tvShowsDetailsResult.getPosterPath());
            }

            navController.navigate(R.id.action_mainMovieFragment_to_noteFragment, bundle);
        }
    }
}