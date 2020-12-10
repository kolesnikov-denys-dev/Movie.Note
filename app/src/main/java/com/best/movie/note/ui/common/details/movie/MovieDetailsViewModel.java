package com.best.movie.note.ui.common.details.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.best.movie.note.model.repositories.MoviesDetailsRepository;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;

import java.util.List;

public class MovieDetailsViewModel extends ViewModel {

    private final MoviesDetailsRepository repository;

    public MovieDetailsViewModel() {
        repository = new MoviesDetailsRepository();
    }

    // Movie Region

    public LiveData<List<GenreResult>> getGenresMoviesData() {
        return repository.getGenresMutableLiveData();
    }


    public LiveData<MovieDetailsApiResponse> getMovieDetails() {
        return repository.getMovieDetailMutableLiveData();
    }

    public LiveData<VideosApiResponse> getMovieVideos() {
        return repository.getMovieVideosLiveData();
    }


    public LiveData<List<MovieResult>> getRecommendations() {
        return repository.getRecommendationsLiveData();
    }

    public LiveData<List<MovieResult>> getSimilar() {
        return repository.getTvShowSimilarLiveData();
    }

    public LiveData<CastCrewApiResponse> getCredits() {
        return repository.getStvShowCastCrewLiveData();
    }
    // End Region Movie


    // Movie Region Update
    public LiveData<MovieDetailsApiResponse> updateMovieDetails(int movieId, String language) {
        return repository.updateMovieDetailLiveData(movieId, language);
    }

    public LiveData<VideosApiResponse> updateMovieVideos(int movieId, String language) {
        return repository.updateMovieVideosLiveData(movieId, language);
    }

    public LiveData<List<GenreResult>> updateGenresMoviesData() {
        return repository.updateGenresMoviesMutableLiveData();
    }

    public LiveData<List<MovieResult>> updateRecommendations(int movieId, String language) {
        return repository.updateRecommendationsLiveData(movieId, language);
    }

    public LiveData<List<MovieResult>> updateSimilar(int movieId, String language) {
        return repository.updateSimilarLiveData(movieId, language);
    }

    public LiveData<CastCrewApiResponse> updateCredits(int movieId, String language) {
        return repository.updateCreditsLiveData(movieId, language);
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
