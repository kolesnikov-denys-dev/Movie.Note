package com.best.movie.note.model.repositories;

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

import static com.best.movie.note.MovieApplication.getAppComponent;
import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;

public class MoviesRepository {
    @Inject
    ApiService apiService;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Throwable> errors = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> popularMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> playingNowMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> trendingMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> topRatedMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> upcomingMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<GenreResult>> genresMutableLiveData = new MutableLiveData<>();

    public MoviesRepository() {
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectMoviesRepository(this);
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public void clearErrors() {
        errors.setValue(null);
    }

    public MutableLiveData<List<MovieResult>> updatePopularMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getPopularMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            popularMutableLiveData.setValue((ArrayList<MovieResult>) moviesApiResponse.getResults());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
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
                            playingNowMutableLiveData.setValue((ArrayList<MovieResult>) moviesApiResponse.getResults());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
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
                            trendingMutableLiveData.setValue((ArrayList<MovieResult>) moviesApiResponse.getResults());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
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
                            topRatedMutableLiveData.setValue((ArrayList<MovieResult>) moviesApiResponse.getResults());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
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
                            upcomingMutableLiveData.setValue((ArrayList<MovieResult>) moviesApiResponse.getResults());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
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
                            genresMutableLiveData.setValue((ArrayList<GenreResult>) moviesApiResponse.getGenres());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
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