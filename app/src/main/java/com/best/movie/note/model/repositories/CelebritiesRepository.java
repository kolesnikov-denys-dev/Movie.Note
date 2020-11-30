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
import com.best.movie.note.model.response.tvshows.persons.popular.PopularPersonApiResponse;
import com.best.movie.note.model.response.tvshows.persons.trending.TrendingPersonApiResponse;
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

public class CelebritiesRepository extends Application {

    private Application application;

    @Inject
    ApiService apiService;

    private CompositeDisposable compositeDisposable;

    public CelebritiesRepository(Application application) {
        this.application = application;
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectCelebritiesRepository(this);
    }

    private PopularPersonApiResponse popularPersonResult;
    private final MutableLiveData<PopularPersonApiResponse> popularPersonMutableLiveData = new MutableLiveData<>();

    private TrendingPersonApiResponse trendingPersonResult;
    private final MutableLiveData<TrendingPersonApiResponse> trendingPersonResultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<PopularPersonApiResponse> getPopularPersonMutableLiveData(String language, String page) {
        Disposable disposableSimpleData = apiService.getPopularPersons(API_KEY, language, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PopularPersonApiResponse>() {
                    @Override
                    public void accept(PopularPersonApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse != null) {
                            popularPersonResult = moviesApiResponse;
                            popularPersonMutableLiveData.setValue(popularPersonResult);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getPopularPersonMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return popularPersonMutableLiveData;
    }

    public MutableLiveData<TrendingPersonApiResponse> getTrendingPersonMutableLiveData(String language) {
        Disposable disposableSimpleData = apiService.getTrendingPersons(API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrendingPersonApiResponse>() {
                    @Override
                    public void accept(TrendingPersonApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse != null) {
                            trendingPersonResult = moviesApiResponse;
                            trendingPersonResultMutableLiveData.setValue(trendingPersonResult);
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
        return trendingPersonResultMutableLiveData;
    }

}
