package com.best.movie.note.ui.fragments.movies.databinding;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.best.movie.note.service.ApiService;

public class MovieDataSourceFactory extends DataSource.Factory {
    private Application application;
    private MovieDataSource movieDataSource;
    private ApiService apiService;
    private MutableLiveData<MovieDataSource> mutableLiveData;

    public MovieDataSourceFactory(Application application, ApiService apiService) {
        this.application = application;
        this.apiService = apiService;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        movieDataSource = new MovieDataSource(apiService, application);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}