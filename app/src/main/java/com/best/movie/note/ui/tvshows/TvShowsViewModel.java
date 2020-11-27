package com.best.movie.note.ui.tvshows;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.TvShowsRepository;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

import java.util.List;

public class TvShowsViewModel extends AndroidViewModel {

    private TvShowsRepository repository;

    public TvShowsViewModel(@NonNull Application application) {
        super(application);

        repository = new TvShowsRepository(application);
    }

    public LiveData<List<MovieResult>> getAiringTodayData() {
        return repository.getAiringTodayMutableLiveData();
    }

    public LiveData<List<MovieResult>> getTrendingData() {
        return repository.getTrendingMutableLiveData();
    }

    public LiveData<List<MovieResult>> getTopRatedData() {
        return repository.getTopRatedMutableLiveData();
    }

    public LiveData<List<MovieResult>> getPopularData() {
        return repository.getPopularMutableLiveData();
    }

    public LiveData<List<GenreResult>> getGenresTvShowsData() {
        return repository.getGenresTvShowsMutableLiveData();
    }

    public void disposeDisposable() {
        repository.disposeDisposable();
    }

}
