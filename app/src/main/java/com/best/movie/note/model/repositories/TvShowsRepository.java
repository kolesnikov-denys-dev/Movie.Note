package com.best.movie.note.model.repositories;

import static com.best.movie.note.MovieApplication.getAppComponent;
import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;
import static com.best.movie.note.utils.Constants.TAG_ERROR;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TvShowsRepository {
    private Application application;
    @Inject
    ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public TvShowsRepository(Application application) {
        this.application = application;
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectTvShowsViewModel(this);
    }

    // Airing Today
    private ArrayList<MovieResult> airingTodayTvShowsResults;
    private final MutableLiveData<List<MovieResult>> airingTodayTvShowsMutableLiveData = new MutableLiveData<>();
    // Trending
    private ArrayList<MovieResult> nowPlayingTvShowsResults;
    private final MutableLiveData<List<MovieResult>> playingNowTvShowsMutableLiveData = new MutableLiveData<>();
    // Top Rated
    private ArrayList<MovieResult> trendingTvShowsResults;
    private final MutableLiveData<List<MovieResult>> trendingTvShowsMutableLiveData = new MutableLiveData<>();
    // Popular
    private ArrayList<MovieResult> topRatedTvShowsResults;
    private final MutableLiveData<List<MovieResult>> topRatedTvShowsMutableLiveData = new MutableLiveData<>();
    // Genres Movies
    private ArrayList<GenreResult> genreTvShowsResults;
    private final MutableLiveData<List<GenreResult>> genresTvShowsMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<MovieResult>> getAiringTodayMutableLiveData() {
        Disposable disposableSimpleData = apiService.getAiringTodayTvShows(API_KEY, QUERY_LANGUAGE, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            airingTodayTvShowsResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            airingTodayTvShowsMutableLiveData.setValue(airingTodayTvShowsResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getAiringTodayMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return airingTodayTvShowsMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTrendingMutableLiveData() {
        Disposable disposableSimpleData = apiService.getTrendingTvShows(API_KEY, QUERY_LANGUAGE, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            trendingTvShowsResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            trendingTvShowsMutableLiveData.setValue(trendingTvShowsResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getTrendingMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return trendingTvShowsMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTopRatedMutableLiveData() {
        Disposable disposableSimpleData = apiService.getTopRatedTvShows(API_KEY, QUERY_LANGUAGE, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            topRatedTvShowsResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            topRatedTvShowsMutableLiveData.setValue(topRatedTvShowsResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getTopRatedMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return topRatedTvShowsMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getPopularMutableLiveData() {
        Disposable disposableSimpleData = apiService.getPopularTvShows(API_KEY, QUERY_LANGUAGE, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            topRatedTvShowsResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            topRatedTvShowsMutableLiveData.setValue(topRatedTvShowsResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getPopularMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return topRatedTvShowsMutableLiveData;
    }

    public MutableLiveData<List<GenreResult>> getGenresTvShowsMutableLiveData() {
        Disposable disposableSimpleData = apiService.getGenresTvShows(API_KEY, QUERY_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenresMovieApiResponse>() {
                    @Override
                    public void accept(GenresMovieApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getGenres() != null) {
                            genreTvShowsResults = (ArrayList<GenreResult>) moviesApiResponse.getGenres();
                            genresTvShowsMutableLiveData.setValue(genreTvShowsResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getGenresTvShowsMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return genresTvShowsMutableLiveData;
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}