package com.best.movie.note.ui.common.details.season;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.repositories.CelebrityDetailsRepository;
import com.best.movie.note.model.repositories.SeasonsRepository;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.tvshows.details.Season;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;
import com.best.movie.note.model.response.tvshows.seasons.SeasonApiResponse;

import java.util.List;

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
