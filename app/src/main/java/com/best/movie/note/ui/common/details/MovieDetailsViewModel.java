package com.best.movie.note.ui.common.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.MoviesRepository;
import com.best.movie.note.model.movies.main.details.MovieDetailsApiResponse;
import com.best.movie.note.model.movies.main.videos.VideosApiResponse;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);

        moviesRepository = new MoviesRepository(application);
    }

    public LiveData<MovieDetailsApiResponse> getMovieDetails(int movieId, String language) {
        return moviesRepository.getMovieDetailLiveData(movieId, language);
    }

    public LiveData<VideosApiResponse> getMovieVideos(int movieId, String language) {
        return moviesRepository.getMovieVideosLiveData(movieId, language);
    }

}
