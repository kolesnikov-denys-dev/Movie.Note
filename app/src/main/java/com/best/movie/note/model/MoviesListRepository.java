package com.best.movie.note.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.service.ApiFactory;
import com.best.movie.note.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;
import static com.best.movie.note.utils.Constants.TAG_ERROR;

public class MoviesListRepository {
    private Application application;
    private ApiFactory apiFactory;
    private ApiService apiService;

    public MoviesListRepository(Application application) {
        this.application = application;
        this.apiFactory = ApiFactory.getInstance();
        this.apiService = apiFactory.getApiService();
    }

    // Popular Movies
    private ArrayList<MovieResult> movieResults;
    private final MutableLiveData<List<MovieResult>> mutableLiveData = new MutableLiveData<>();
    // Playing Now Movies
    private ArrayList<MovieResult> nowPlayingResults;
    private final MutableLiveData<List<MovieResult>> playingNowMutableLiveData = new MutableLiveData<>();
    // Trending Movies
    private ArrayList<MovieResult> trendingResults;
    private final MutableLiveData<List<MovieResult>> trendingMutableLiveData = new MutableLiveData<>();
    // Top Rated Movies
    private ArrayList<MovieResult> topRatedResults;
    private final MutableLiveData<List<MovieResult>> topRatedMutableLiveData = new MutableLiveData<>();
    // Upcoming Movies
    private ArrayList<MovieResult> upcomingResults;
    private final MutableLiveData<List<MovieResult>> upcomingMutableLiveData = new MutableLiveData<>();
    // Genres Movies
    private ArrayList<GenreResult> genreResults;
    private final MutableLiveData<List<GenreResult>> genresMutableLiveData = new MutableLiveData<>();
    // Paging Library
    private ArrayList<MovieResult> results = new ArrayList<>();
    private MutableLiveData<List<MovieResult>> mutablePagingLiveData = new MutableLiveData<>();

    public MutableLiveData<List<MovieResult>> getMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<MovieResult>) movieApiResponse.getResults();
                    mutablePagingLiveData.setValue(results);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
        return mutablePagingLiveData;
    }

    public MutableLiveData<List<MovieResult>> getPopularMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                    movieResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                    mutableLiveData.setValue(movieResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getPopularMoviesMutableLiveData" + t.getLocalizedMessage());
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getNowPlayingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getNowPlayingMovies(API_KEY);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse playingNowMoviesApiResponse = response.body();
                if (playingNowMoviesApiResponse != null && playingNowMoviesApiResponse.getResults() != null) {
                    nowPlayingResults = (ArrayList<MovieResult>) playingNowMoviesApiResponse.getResults();
                    playingNowMutableLiveData.setValue(nowPlayingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getNowPlayingMoviesMutableLiveData" + t.getLocalizedMessage());
            }
        });
        return playingNowMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTrendingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getTrendingMovies(API_KEY);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getResults() != null) {
                    trendingResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getResults();
                    trendingMutableLiveData.setValue(trendingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getTrendingMoviesMutableLiveData" + t.getLocalizedMessage());
            }
        });
        return trendingMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTopRatedMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getResults() != null) {
                    topRatedResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getResults();
                    topRatedMutableLiveData.setValue(topRatedResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getTopRatedMoviesMutableLiveData" + t.getLocalizedMessage());
            }
        });
        return topRatedMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getUpcomingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService
                .getUpcomingMovies(API_KEY,
                        QUERY_LANGUAGE,
                        "1");
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getResults() != null) {
                    upcomingResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getResults();
                    upcomingMutableLiveData.setValue(upcomingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getUpcomingMoviesMutableLiveData" + t.getLocalizedMessage());
            }
        });
        return upcomingMutableLiveData;
    }

    public MutableLiveData<List<GenreResult>> getGenresMoviesMutableLiveData() {
        Call<GenresMovieApiResponse> call = apiService.getGenresMovies(API_KEY,
                QUERY_LANGUAGE);
        call.enqueue(new Callback<GenresMovieApiResponse>() {
            @Override
            public void onResponse(Call<GenresMovieApiResponse> call, Response<GenresMovieApiResponse> response) {
                GenresMovieApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getGenres() != null) {
                    genreResults = (ArrayList<GenreResult>) trendingMoviesApiResponse.getGenres();
                    genresMutableLiveData.setValue(genreResults);
                }
            }

            @Override
            public void onFailure(Call<GenresMovieApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getGenresMoviesMutableLiveData" + t.getLocalizedMessage());
            }
        });
        return genresMutableLiveData;
    }

}
