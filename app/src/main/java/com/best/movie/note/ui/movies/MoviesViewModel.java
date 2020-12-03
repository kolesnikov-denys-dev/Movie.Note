package com.best.movie.note.ui.movies;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.best.movie.note.model.repositories.MoviesRepository;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

import java.util.List;

public class MoviesViewModel extends ViewModel {

    private MoviesRepository moviesRepository;

    public MoviesViewModel() {
        moviesRepository = new MoviesRepository();
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

    public LiveData<List<GenreResult>> getGenresMoviesData() {
        return moviesRepository.getGenresMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> updatePopularMoviesData() {
        return moviesRepository.updatePopularMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> updateNowPlayingMoviesData() {
        return moviesRepository.updateNowPlayingMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> updateTrendingMoviesData() {
        return moviesRepository.updateTrendingMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> updateTopRatedMoviesData() {
        return moviesRepository.updateTopRatedMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> updateUpcomingMoviesData() {
        return moviesRepository.updateUpcomingMoviesMutableLiveData();
    }

    public LiveData<List<GenreResult>> updateGenresMoviesData() {
        return moviesRepository.updateGenresMoviesMutableLiveData();
    }

    public LiveData<Throwable> getErrors() {
        return moviesRepository.getErrors();
    }

    public void clearErrors() {
        moviesRepository.clearErrors();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        moviesRepository.disposeDisposable();
    }

}
