package com.best.movie.note.model.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.Cast;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;
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
import static com.best.movie.note.utils.Constants.TAG_ERROR;

public class CelebrityDetailsRepository extends Application {

    private Application application;

    @Inject
    ApiService apiService;

    private CompositeDisposable compositeDisposable;

    public CelebrityDetailsRepository(Application application) {
        this.application = application;
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectCelebrityDetailsRepository(this);
    }

    private CastDetailsApiResponse castDetailsResult;
    private final MutableLiveData<CastDetailsApiResponse> castDetailsMutableLiveData = new MutableLiveData<>();

    private MoviesCastApiResponse moviesCastResult;
    private final MutableLiveData<MoviesCastApiResponse> moviesCastResultMutableLiveData = new MutableLiveData<>();

    private TvShowsCatApiResponse tvShowsCatResult;
    private final MutableLiveData<TvShowsCatApiResponse> tvShowsCatResultMutableLiveData = new MutableLiveData<>();


    // Genres Movies
    private ArrayList<GenreResult> genreResults;
    private final MutableLiveData<List<GenreResult>> genresMutableLiveData = new MutableLiveData<>();

    private ArrayList<GenreResult> tvShowsGenresResult;
    private final MutableLiveData<List<GenreResult>> tvShowsGenresResultMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<CastDetailsApiResponse> getCastDetailsMutableLiveData(int castId, String language) {
        Disposable disposableSimpleData = apiService.getCastDetailsById(castId, API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CastDetailsApiResponse>() {
                    @Override
                    public void accept(CastDetailsApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse != null) {
                            castDetailsResult = moviesApiResponse;
                            castDetailsMutableLiveData.setValue(castDetailsResult);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getCastDetailsMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return castDetailsMutableLiveData;
    }

    public MutableLiveData<MoviesCastApiResponse> getMoviesCastResultMutableLiveData(int castId, String language) {
        Disposable disposableSimpleData = apiService.getMoviesByCastId(castId, API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesCastApiResponse>() {
                    @Override
                    public void accept(MoviesCastApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse != null) {
                            moviesCastResult = moviesApiResponse;
                            moviesCastResultMutableLiveData.setValue(moviesCastResult);
//                            for (Cast x : moviesCastResult.getCast()){
//                                Log.i("check", " ---------" + x.getTitle());
//                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getMoviesCastResultMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return moviesCastResultMutableLiveData;
    }


    public MutableLiveData<TvShowsCatApiResponse> getTvShowsCatMutableLiveData(int castId, String language) {
        Disposable disposableSimpleData = apiService.getTvShowsByCastId(castId, API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TvShowsCatApiResponse>() {
                    @Override
                    public void accept(TvShowsCatApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse != null) {
                            tvShowsCatResult = moviesApiResponse;
                            tvShowsCatResultMutableLiveData.setValue(tvShowsCatResult);



                            Log.i("check", "tvShowsCatResult----SIZE!!!!!>>>" + tvShowsCatResult.getCast());

                            for (Cast x : tvShowsCatResult.getCast()) {
                                Log.i("check", "tvShowsCatResult---->>>" + x.getOriginalName());
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getTvShowsCatMutableLiveData" + throwable.getLocalizedMessage());
                        Log.e(TAG_ERROR, "+++++++++++++++++++++" + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return tvShowsCatResultMutableLiveData;
    }


    public MutableLiveData<List<GenreResult>> getGenresMoviesMutableLiveData() {
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
                        Log.e(TAG_ERROR, "onFailure: getUpcomingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return genresMutableLiveData;
    }


    public MutableLiveData<List<GenreResult>> getGenresTvShowsMutableLiveData() {
        Disposable disposableSimpleData = apiService.getGenresTvShows(API_KEY, QUERY_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenresMovieApiResponse>() {
                    @Override
                    public void accept(GenresMovieApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getGenres() != null) {
                            tvShowsGenresResult = (ArrayList<GenreResult>) moviesApiResponse.getGenres();
                            tvShowsGenresResultMutableLiveData.setValue(tvShowsGenresResult);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getUpcomingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return tvShowsGenresResultMutableLiveData;
    }
}