package com.best.movie.note.ui.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.best.movie.note.model.repositories.MoviesRepository;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;

import java.util.List;

public class MoviesViewModel extends ViewModel {

    private MoviesRepository moviesRepository;
    private MutableLiveData<List<MovieResult>> popularMutableLiveData;
    private MutableLiveData<List<MovieResult>> playingNowMutableLiveData;
    private MutableLiveData<List<MovieResult>> trendingMutableLiveData;
    private MutableLiveData<List<MovieResult>> topRatedMutableLiveData;
    private MutableLiveData<List<MovieResult>> upcomingMutableLiveData;
    private MutableLiveData<List<GenreResult>> genresMutableLiveData;

    public MoviesViewModel() {
        moviesRepository = new MoviesRepository();
    }

    public LiveData<List<MovieResult>> getPopularMoviesData() {
        if (popularMutableLiveData == null) {
            popularMutableLiveData = moviesRepository.updatePopularMoviesMutableLiveData();
        }
        return popularMutableLiveData;
    }

    public LiveData<List<MovieResult>> getNowPlayingMoviesData() {
        if (playingNowMutableLiveData == null) {
            playingNowMutableLiveData = moviesRepository.updateNowPlayingMoviesMutableLiveData();
        }
        return popularMutableLiveData;
    }

    public LiveData<List<MovieResult>> getTrendingMoviesData() {
        if (trendingMutableLiveData == null) {
            trendingMutableLiveData = moviesRepository.updateTrendingMoviesMutableLiveData();
        }
        return trendingMutableLiveData;
    }

    public LiveData<List<MovieResult>> getTopRatedMoviesData() {
        if (topRatedMutableLiveData == null) {
            topRatedMutableLiveData = moviesRepository.updateTopRatedMoviesMutableLiveData();
        }
        return topRatedMutableLiveData;
    }

    public LiveData<List<MovieResult>> getUpcomingMoviesData() {
        if (upcomingMutableLiveData == null) {
            upcomingMutableLiveData = moviesRepository.updateUpcomingMoviesMutableLiveData();
        }
        return upcomingMutableLiveData;
    }

    public LiveData<List<GenreResult>> getGenresMoviesData() {
        if (genresMutableLiveData == null) {
            genresMutableLiveData = moviesRepository.updateGenresMoviesMutableLiveData();
        }
        return genresMutableLiveData;
    }
//
//    public void updatePopularMoviesData() {
//        popularMutableLiveData = moviesRepository.updatePopularMoviesMutableLiveData();
//    }
//
//    public void updateNowPlayingMoviesData() {
//        playingNowMutableLiveData = moviesRepository.updateNowPlayingMoviesMutableLiveData();
//    }
//
//    public void updateTrendingMoviesData() {
//        trendingMutableLiveData = moviesRepository.updateTrendingMoviesMutableLiveData();
//    }
//
//    public void updateTopRatedMoviesData() {
//        topRatedMutableLiveData = moviesRepository.updateTopRatedMoviesMutableLiveData();
//    }
//
//    public void updateUpcomingMoviesData() {
//        upcomingMutableLiveData = moviesRepository.updateUpcomingMoviesMutableLiveData();
//    }
//
//    public LiveData<List<GenreResult>> updateGenresMoviesData() {
//        return moviesRepository.updateGenresMoviesMutableLiveData();
//    }

    public LiveData<Throwable> getErrors() {
        return moviesRepository.getErrors();
    }

    public void clearErrors() {
        moviesRepository.clearErrors();
    }

    @Override
    protected void onCleared() {
        moviesRepository.disposeDisposable();
        super.onCleared();
    }

}
