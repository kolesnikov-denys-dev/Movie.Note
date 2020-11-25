package com.best.movie.note.ui.common.list.movies.databinding;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.TAG_ERROR;


public class MovieDataSource extends PageKeyedDataSource<Long, MovieResult> {

    private Application application;
    private ApiService apiService;
    private List<MovieResult> result;

    public MovieDataSource(ApiService apiService, Application application) {
        this.application = application;
        this.apiService = apiService;
        result = new ArrayList<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, MovieResult> callback) {
        Disposable disposableSimpleData = apiService.getPopularMoviesWithPaging(API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            result = moviesApiResponse.getResults();
                            callback.onResult(result, null, (long) 2);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: loadInitial" + throwable.getLocalizedMessage());
                    }
                });
//        compositeDisposable.add(disposableSimpleData);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, MovieResult> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull final LoadCallback<Long, MovieResult> callback) {
        Disposable disposableSimpleData = apiService.getPopularMoviesWithPaging(API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesApiResponse>() {
                    @Override
                    public void accept(MoviesApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getResults() != null) {
                            result = moviesApiResponse.getResults();
                            callback.onResult(result, params.key + 1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: loadAfter" + throwable.getLocalizedMessage());
                    }
                });
        //        compositeDisposable.add(disposableSimpleData);
    }

}
