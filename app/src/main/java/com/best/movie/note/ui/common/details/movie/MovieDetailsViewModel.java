package com.best.movie.note.ui.common.details.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.repositories.MoviesDetailsRepository;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;

import java.util.List;

public class MovieDetailsViewModel extends AndroidViewModel {

    private final MoviesDetailsRepository repository;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new MoviesDetailsRepository(application);
    }

    // Movie Region
    public LiveData<MovieDetailsApiResponse> getMovieDetails(int movieId, String language) {
        return repository.getMovieDetailLiveData(movieId, language);
    }

    public LiveData<VideosApiResponse> getMovieVideos(int movieId, String language) {
        return repository.getMovieVideosLiveData(movieId, language);
    }

    public LiveData<List<GenreResult>> getGenresMoviesData() {
        return repository.getGenresMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> getRecommendations(int movieId, String language) {
        return repository.getRecommendationsLiveData(movieId, language);
    }

    public LiveData<List<MovieResult>> getSimilar(int movieId, String language) {
        return repository.getSimilarLiveData(movieId, language);
    }

    public LiveData<CastCrewApiResponse> getCredits(int movieId, String language) {
        return repository.getCreditsLiveData(movieId, language);
    }
    // End Region Movie

    // Tv Shows Region
    public LiveData<TvShowsApiResponse> getTvShowsDetails(int movieId, String language) {
        return repository.getTvShowsDetailLiveData(movieId, language);
    }

    public LiveData<VideosApiResponse> getTvShowsVideos(int movieId, String language) {
        return repository.getTvShowsVideosLiveData(movieId, language);
    }

    public LiveData<List<GenreResult>> getTvShowsGenresMoviesData() {
        return repository.getGenresTvShowsMutableLiveData();
    }

    public LiveData<List<MovieResult>> getTvShowsRecommendations(int movieId, String language) {
        return repository.getTvShowsRecommendationsLiveData(movieId, language);
    }

    public LiveData<List<MovieResult>> getTvShowsSimilar(int movieId, String language) {
        return repository.getTvShowsSimilarLiveData(movieId, language);
    }

    public LiveData<CastCrewApiResponse> getTvShowsCredits(int movieId, String language) {
        return repository.getTvShowsCreditsLiveData(movieId, language);
    }
    // End Region Tv Shows

}
