package com.best.movie.note.ui.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.MoviesRepository;
import com.best.movie.note.model.movies.cards.MovieResult;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        moviesRepository = new MoviesRepository(application);
    }

    public LiveData<List<MovieResult>> getPopularMoviesData() {
        return moviesRepository.getPopularMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> getNowPlayingMoviesData() {
        return moviesRepository.getNowPlayingMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> getTrendingMoviesData() {
        return moviesRepository.getTrendingMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> getTopRatedMoviesData() {
        return moviesRepository.getTopRatedMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> getUpcomingMoviesData() {
        return moviesRepository.getUpcomingMoviesMutableLiveData();
    }

}
