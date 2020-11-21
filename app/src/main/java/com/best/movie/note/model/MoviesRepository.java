package com.best.movie.note.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.best.movie.note.R;
import com.best.movie.note.model.movies.cards.MovieResult;
import com.best.movie.note.model.movies.cards.MoviesApiResponse;
import com.best.movie.note.network.MovieApiService;
import com.best.movie.note.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    private final Application application;
    private final MovieApiService movieApiService;

    // Popular Movies
    private ArrayList<MovieResult> movieResults = new ArrayList<>();
    private final MutableLiveData<List<MovieResult>> mutableLiveData = new MutableLiveData<>();
    // Playing Now Movies
    private ArrayList<MovieResult> nowPlayingResults = new ArrayList<>();
    private final MutableLiveData<List<MovieResult>> playingNowMutableLiveData = new MutableLiveData<>();
    // Trending Movies
    private ArrayList<MovieResult> trendingResults = new ArrayList<>();
    private final MutableLiveData<List<MovieResult>> trendingMutableLiveData = new MutableLiveData<>();
    // Top Rated Movies
    private ArrayList<MovieResult> topRatedResults = new ArrayList<>();
    private final MutableLiveData<List<MovieResult>> topRatedMutableLiveData = new MutableLiveData<>();
    // Upcoming Movies
    private ArrayList<MovieResult> upcomingResults = new ArrayList<>();
    private final MutableLiveData<List<MovieResult>> upcomingMutableLiveData = new MutableLiveData<>();

    public MoviesRepository(Application application) {
        this.application = application;
        this.movieApiService = RetrofitInstance.getService();
    }

    public MutableLiveData<List<MovieResult>> getPopularMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = movieApiService.getPopularMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse.getPopularResults() != null) {
                    movieResults = (ArrayList<MovieResult>) moviesApiResponse.getPopularResults();
                    mutableLiveData.setValue(movieResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getNowPlayingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = movieApiService.getNowPlayingMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse playingNowMoviesApiResponse = response.body();
                if (playingNowMoviesApiResponse != null && playingNowMoviesApiResponse.getPopularResults() != null) {
                    nowPlayingResults = (ArrayList<MovieResult>) playingNowMoviesApiResponse.getPopularResults();
                    playingNowMutableLiveData.setValue(nowPlayingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
        return playingNowMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTrendingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = movieApiService.getTrendingMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getPopularResults() != null) {
                    trendingResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getPopularResults();
                    trendingMutableLiveData.setValue(trendingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.i("check", "ERROR: ");
            }
        });
        return trendingMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTopRatedMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = movieApiService.getTopRatedMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getPopularResults() != null) {
                    topRatedResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getPopularResults();
                    topRatedMutableLiveData.setValue(topRatedResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
            }
        });
        return topRatedMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getUpcomingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = movieApiService
                .getUpcomingMovies(application.getApplicationContext()
                .getString(R.string.api_key),
                "en-US",
                "1");
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                Log.d("check", "getUpcomingMoviesMutableLiveData : " + response.toString());
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getPopularResults() != null) {
                    upcomingResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getPopularResults();
                    upcomingMutableLiveData.setValue(upcomingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.d("check", "onFailure : " + t.getLocalizedMessage());
            }
        });
        return upcomingMutableLiveData;
    }

}