package com.best.movie.note.ui.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.MoviesRepository;
import com.best.movie.note.model.movies.nowplaying.NowPlayingResult;
import com.best.movie.note.model.movies.popular.PopularResult;
import com.best.movie.note.model.movies.toprated.TopRatedResult;
import com.best.movie.note.model.movies.trending.TrendingResult;
import com.best.movie.note.model.movies.upcoming.UpcomingResult;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        moviesRepository = new MoviesRepository(application);
    }

    public LiveData<List<PopularResult>> getPopularMoviesData() {
        return moviesRepository.getPopularMoviesMutableLiveData();
    }

    public LiveData<List<NowPlayingResult>> getNowPlayingMoviesData() {
        return moviesRepository.getNowPlayingMoviesMutableLiveData();
    }

    public LiveData<List<TrendingResult>> getTrendingMoviesData() {
        return moviesRepository.getTrendingMoviesMutableLiveData();
    }

    public LiveData<List<TopRatedResult>> getTopRatedMoviesData() {
        return moviesRepository.getTopRatedMoviesMutableLiveData();
    }

    public LiveData<List<UpcomingResult>> getUpcomingMoviesData() {
        return moviesRepository.getUpcomingMoviesMutableLiveData();
    }

}
