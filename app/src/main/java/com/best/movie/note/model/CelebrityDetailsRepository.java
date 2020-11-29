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
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.best.movie.note.Global.getAppComponent;
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

    private ArrayList<MovieResult> moviesCastResult;
    private final MutableLiveData<List<MovieResult>> moviesCastResultMutableLiveData = new MutableLiveData<>();

    private ArrayList<MovieResult> tvShowsCatResult;
    private final MutableLiveData<List<MovieResult>> tvShowsCatResultMutableLiveData = new MutableLiveData<>();




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

    public MutableLiveData<List<MovieResult>> getMoviesCastResultMutableLiveData(int castId, String language) {
        Disposable disposableSimpleData = apiService.getMoviesByCastId(castId, API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse != null) {
                            moviesCastResult = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            moviesCastResultMutableLiveData.setValue(moviesCastResult);

                            Log.e(TAG_ERROR, " ---------++++++  Movies "+moviesCastResult.toString() );

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getMoviesCastResultMutableLiveData" + throwable.getLocalizedMessage());
                        Log.e(TAG_ERROR, "-------------------" + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return moviesCastResultMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTvShowsCatMutableLiveData(int castId, String language) {
        Disposable disposableSimpleData = apiService.getTvShowsByCastId(castId, API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse != null) {
                            tvShowsCatResult = (ArrayList<MovieResult>) moviesApiResponse.getResults();
                            tvShowsCatResultMutableLiveData.setValue(tvShowsCatResult);
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
