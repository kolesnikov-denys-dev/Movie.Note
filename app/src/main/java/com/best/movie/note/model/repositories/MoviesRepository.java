package com.best.movie.note.model.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
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

import static com.best.movie.note.Global.getAppComponent;
import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;
import static com.best.movie.note.utils.Constants.TAG_ERROR;

public class MoviesRepository {

    @Inject
    ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public MoviesRepository() {
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectMoviesRepository(this);
    }

    private MutableLiveData<Throwable> errors = new MutableLiveData<>();
    private ArrayList<MovieResult> movieResults;
    private MutableLiveData<List<MovieResult>> popularMutableLiveData;
    private ArrayList<MovieResult> nowPlayingResults;
    private MutableLiveData<List<MovieResult>> playingNowMutableLiveData;
    private ArrayList<MovieResult> trendingResults;
    private MutableLiveData<List<MovieResult>> trendingMutableLiveData;
    private ArrayList<MovieResult> topRatedResults;
    private MutableLiveData<List<MovieResult>> topRatedMutableLiveData;
    private ArrayList<MovieResult> upcomingResults;
    private MutableLiveData<List<MovieResult>> upcomingMutableLiveData;
    private ArrayList<GenreResult> genreResults;
    private MutableLiveData<List<GenreResult>> genresMutableLiveData;

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public void clearErrors() {
        errors.setValue(null);
    }

    public MutableLiveData<List<MovieResult>> getPopularMoviesMutableLiveData() {
        if (popularMutableLiveData == null) {
            popularMutableLiveData = new MutableLiveData<>();
            updatePopularMoviesMutableLiveData();
        }
        return this.popularMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getNowPlayingMoviesMutableLiveData() {
        if (playingNowMutableLiveData == null) {
            playingNowMutableLiveData = new MutableLiveData<>();
            updateNowPlayingMoviesMutableLiveData();
        }
        return this.playingNowMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTrendingMoviesMutableLiveData() {
        if (trendingMutableLiveData == null) {
            trendingMutableLiveData = new MutableLiveData<>();
            updateTrendingMoviesMutableLiveData();
        }
        return this.trendingMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTopRatedMoviesMutableLiveData() {
        if (topRatedMutableLiveData == null) {
            topRatedMutableLiveData = new MutableLiveData<>();
            updateTopRatedMoviesMutableLiveData();
        }
        return this.topRatedMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getUpcomingMoviesMutableLiveData() {
        if (upcomingMutableLiveData == null) {
            upcomingMutableLiveData = new MutableLiveData<>();
            updateUpcomingMoviesMutableLiveData();
        }
        return this.upcomingMutableLiveData;
    }

    public MutableLiveData<List<GenreResult>> getGenresMoviesMutableLiveData() {
        if (genresMutableLiveData == null) {
            genresMutableLiveData = new MutableLiveData<>();
            updateGenresMoviesMutableLiveData();
        }
        return this.genresMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> updatePopularMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getPopularMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            movieResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            popularMutableLiveData.setValue(movieResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                        Log.e(TAG_ERROR, "accept: getPopularMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return popularMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> updateNowPlayingMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getNowPlayingMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            nowPlayingResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            playingNowMutableLiveData.setValue(nowPlayingResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                        Log.e(TAG_ERROR, "accept: getNowPlayingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return playingNowMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> updateTrendingMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getTrendingMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            trendingResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            trendingMutableLiveData.setValue(trendingResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                        Log.e(TAG_ERROR, "accept: getTrendingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return trendingMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> updateTopRatedMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getTopRatedMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            topRatedResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            topRatedMutableLiveData.setValue(topRatedResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                        Log.e(TAG_ERROR, "accept: getTopRatedMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return topRatedMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> updateUpcomingMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getUpcomingMovies(API_KEY, QUERY_LANGUAGE,
                "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            upcomingResults = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            upcomingMutableLiveData.setValue(upcomingResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                        Log.e(TAG_ERROR, "accept: getUpcomingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return upcomingMutableLiveData;
    }

    public MutableLiveData<List<GenreResult>> updateGenresMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getGenresMovies(API_KEY, QUERY_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenresMovieApiResponse>() {
                    @Override
                    public void accept(GenresMovieApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getGenres() != null) {
                            genreResults = (ArrayList<GenreResult>) moviesApiResponse.getGenres();
                            genresMutableLiveData.setValue(genreResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                        Log.e(TAG_ERROR, "accept: getUpcomingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return genresMutableLiveData;
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}
