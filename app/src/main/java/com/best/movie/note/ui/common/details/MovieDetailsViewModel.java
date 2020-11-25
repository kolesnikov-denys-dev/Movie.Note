package com.best.movie.note.ui.common.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.MoviesRepository;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;

import java.util.List;

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


    public LiveData<List<GenreResult>> getGenresMoviesData() {
        return moviesRepository.getGenresMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> getRecommendations(int movieId, String language) {
        return moviesRepository.getRecommendationsLiveData(movieId, language);
    }

    public LiveData<List<MovieResult>> getSimilar(int movieId, String language) {
        return moviesRepository.getSimilarLiveData(movieId, language);
    }

    public LiveData<CastCrewApiResponse> getCredits(int movieId, String language) {
        return moviesRepository.getCreditsLiveData(movieId, language);
    }


}
