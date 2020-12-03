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
    private MutableLiveData<List<MovieResult>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<MovieResult> nowPlayingResults;
    private MutableLiveData<List<MovieResult>> playingNowMutableLiveData = new MutableLiveData<>();
    private ArrayList<MovieResult> trendingResults;
    private MutableLiveData<List<MovieResult>> trendingMutableLiveData = new MutableLiveData<>();
    private ArrayList<MovieResult> topRatedResults;
    private MutableLiveData<List<MovieResult>> topRatedMutableLiveData = new MutableLiveData<>();
    private ArrayList<MovieResult> upcomingResults;
    private MutableLiveData<List<MovieResult>> upcomingMutableLiveData = new MutableLiveData<>();
    private ArrayList<GenreResult> genreResults;
    private MutableLiveData<List<GenreResult>> genresMutableLiveData = new MutableLiveData<>();

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public void clearErrors() {
        errors.setValue(null);
    }

    public MutableLiveData<List<MovieResult>> getPopularMoviesMutableLiveData() {
        return this.mutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getNowPlayingMoviesMutableLiveData() {
        return this.playingNowMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTrendingMoviesMutableLiveData() {
        return this.trendingMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTopRatedMoviesMutableLiveData() {
        return this.topRatedMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getUpcomingMoviesMutableLiveData() {
        return this.upcomingMutableLiveData;
    }

    public MutableLiveData<List<GenreResult>> getGenresMoviesMutableLiveData() {
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
                            mutableLiveData.setValue(movieResults);
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
        return mutableLiveData;
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
