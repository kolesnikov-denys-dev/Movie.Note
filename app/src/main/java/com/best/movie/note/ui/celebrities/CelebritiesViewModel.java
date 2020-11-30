package com.best.movie.note.ui.celebrities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.repositories.CelebritiesRepository;
import com.best.movie.note.model.response.tvshows.persons.popular.PopularPersonApiResponse;
import com.best.movie.note.model.response.tvshows.persons.trending.TrendingPersonApiResponse;

public class CelebritiesViewModel extends AndroidViewModel {
    private final CelebritiesRepository repository;

    public CelebritiesViewModel(@NonNull Application application) {
        super(application);
        repository = new CelebritiesRepository(application);
    }

    public LiveData<PopularPersonApiResponse> getPopularPerson(String language, String page) {
        return repository.getPopularPersonMutableLiveData(language, page);
    }

    public MutableLiveData<TrendingPersonApiResponse> getTrendingPerson(String language) {
        return repository.getTrendingPersonMutableLiveData(language);
    }

}