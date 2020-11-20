package com.best.movie.note.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;


import com.best.movie.note.R;
import com.best.movie.note.model.movies.nowplaying.NowPlayingResult;
import com.best.movie.note.model.movies.nowplaying.PlayingNowMoviesApiResponse;
import com.best.movie.note.model.movies.popular.PopularResult;
import com.best.movie.note.model.movies.popular.PopularMoviesApiResponse;
import com.best.movie.note.model.movies.trending.TrendingMoviesApiResponse;
import com.best.movie.note.model.movies.trending.TrendingResult;
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
    private ArrayList<PopularResult> popularResults = new ArrayList<>();
    private final MutableLiveData<List<PopularResult>> mutableLiveData = new MutableLiveData<>();
    // Playing Now Movies
    private ArrayList<NowPlayingResult> nowPlayingResults = new ArrayList<>();
    private final MutableLiveData<List<NowPlayingResult>> playingNowMutableLiveData = new MutableLiveData<>();
    // Trending Movies
    private ArrayList<TrendingResult> trendingResults = new ArrayList<>();
    private final MutableLiveData<List<TrendingResult>> trendingMutableLiveData = new MutableLiveData<>();

    public MoviesRepository(Application application) {
        this.application = application;
        this.movieApiService = RetrofitInstance.getService();
    }

    public MutableLiveData<List<PopularResult>> getPopularMoviesMutableLiveData() {
        Call<PopularMoviesApiResponse> call = movieApiService.getPopularMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<PopularMoviesApiResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesApiResponse> call, Response<PopularMoviesApiResponse> response) {
                PopularMoviesApiResponse popularMoviesApiResponse = response.body();
                if (popularMoviesApiResponse != null && popularMoviesApiResponse.getPopularResults() != null) {
                    popularResults = (ArrayList<PopularResult>) popularMoviesApiResponse.getPopularResults();
                    mutableLiveData.setValue(popularResults);
                }
            }

            @Override
            public void onFailure(Call<PopularMoviesApiResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<List<NowPlayingResult>> getNowPlayingMoviesMutableLiveData() {
        Call<PlayingNowMoviesApiResponse> call = movieApiService.getNowPlayingMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<PlayingNowMoviesApiResponse>() {
            @Override
            public void onResponse(Call<PlayingNowMoviesApiResponse> call, Response<PlayingNowMoviesApiResponse> response) {
                PlayingNowMoviesApiResponse playingNowMoviesApiResponse = response.body();
                if (playingNowMoviesApiResponse != null && playingNowMoviesApiResponse.getNowPlayingResults() != null) {
                    nowPlayingResults = (ArrayList<NowPlayingResult>) playingNowMoviesApiResponse.getNowPlayingResults();
                    playingNowMutableLiveData.setValue(nowPlayingResults);
                }
            }

            @Override
            public void onFailure(Call<PlayingNowMoviesApiResponse> call, Throwable t) {

            }
        });
        return playingNowMutableLiveData;
    }

    public MutableLiveData<List<TrendingResult>> getTrendingMoviesMutableLiveData() {
        Call<TrendingMoviesApiResponse> call = movieApiService.getTrendingMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<TrendingMoviesApiResponse>() {
            @Override
            public void onResponse(Call<TrendingMoviesApiResponse> call, Response<TrendingMoviesApiResponse> response) {
                TrendingMoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getResults() != null) {
                    trendingResults = (ArrayList<TrendingResult>) trendingMoviesApiResponse.getResults();
                    trendingMutableLiveData.setValue(trendingResults);
                }
            }

            @Override
            public void onFailure(Call<TrendingMoviesApiResponse> call, Throwable t) {

            }
        });
        return trendingMutableLiveData;
    }


}
