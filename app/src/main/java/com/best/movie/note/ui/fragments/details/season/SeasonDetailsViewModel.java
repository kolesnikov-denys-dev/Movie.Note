package com.best.movie.note.ui.fragments.details.season;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.repositories.SeasonsRepository;
import com.best.movie.note.model.response.tvshows.seasons.SeasonApiResponse;

public class SeasonDetailsViewModel extends AndroidViewModel {
    private final SeasonsRepository repository;

    public SeasonDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new SeasonsRepository(application);
    }

    public LiveData<SeasonApiResponse> getSeason(int tvId, int seasonNumber, String language) {
        return repository.getSeasonMutableLiveData(tvId, seasonNumber, language);
    }
}