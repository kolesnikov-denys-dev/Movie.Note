package com.best.movie.note.model.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.model.response.tvshows.seasons.SeasonApiResponse;
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

public class SeasonsRepository {
    private Application application;

    @Inject
    ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public SeasonsRepository(Application application) {
        this.application = application;
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectSeasonsRepository(this);
    }

    // Airing Today
    private SeasonApiResponse seasonResult;
    private final MutableLiveData<SeasonApiResponse> seasonMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<SeasonApiResponse> getSeasonMutableLiveData(int tvId, int seasonNumber, String language) {
        Disposable disposableSimpleData = apiService.getSeasonByTvShowId(tvId, seasonNumber, API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SeasonApiResponse>() {
                    @Override
                    public void accept(SeasonApiResponse seasonApiResponse) throws Exception {
                        if (seasonApiResponse != null && seasonApiResponse != null) {
                            seasonResult = seasonApiResponse;
                            seasonMutableLiveData.setValue(seasonResult);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: geSeasonMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return seasonMutableLiveData;
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}
