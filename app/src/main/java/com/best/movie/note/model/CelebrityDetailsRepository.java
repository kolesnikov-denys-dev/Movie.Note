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

    private MoviesCastApiResponse moviesCastResult;
    private final MutableLiveData<MoviesCastApiResponse> moviesCastResultMutableLiveData = new MutableLiveData<>();

    private TvShowsCatApiResponse tvShowsCatResult;
    private final MutableLiveData<TvShowsCatApiResponse> tvShowsCatResultMutableLiveData = new MutableLiveData<>();

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
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getTvShowsCatMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return tvShowsCatResultMutableLiveData;
    }

}
