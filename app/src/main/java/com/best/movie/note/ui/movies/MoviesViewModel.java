package com.best.movie.note.ui.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.repositories.MoviesRepository;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

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

    public LiveData<List<GenreResult>> getGenresMoviesData() {
        return moviesRepository.getGenresMoviesMutableLiveData();
    }

    public void disposeDisposable (){
        moviesRepository.disposeDisposable();
    }

}
