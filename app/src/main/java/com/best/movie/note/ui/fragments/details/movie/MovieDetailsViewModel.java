package com.best.movie.note.ui.fragments.details.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    // Movies Region
    private MutableLiveData<List<GenreResult>> genresMutableLiveData;
    private MutableLiveData<MovieDetailsApiResponse> movieDetailsApiResponseMutableLiveData;
    private MutableLiveData<VideosApiResponse> movieVideosApiResponseMutableLiveData;
    private MutableLiveData<List<MovieResult>> recommendationsApiResponseMutableLiveData;
    private MutableLiveData<List<MovieResult>> similarApiResponseMutableLiveData;
    private MutableLiveData<CastCrewApiResponse> castCrewApiResponseMutableLiveData;
    // End Region Movies

    // Tv Shows Region
    private MutableLiveData<List<GenreResult>> tvShowGenresMutableLiveData;
    private MutableLiveData<TvShowsApiResponse> tvShowDetailsApiResponseMutableLiveData;
    private MutableLiveData<VideosApiResponse> tvShowVideosApiResponseMutableLiveData;
    private MutableLiveData<List<MovieResult>> tvShowRecommendationsApiResponseMutableLiveData;
    private MutableLiveData<List<MovieResult>> tvShowSimilarApiResponseMutableLiveData;
    private MutableLiveData<CastCrewApiResponse> tvShowCastCrewApiResponseMutableLiveData;
    // End Region

    public MovieDetailsViewModel() {
        repository = new MoviesDetailsRepository();
    }

    // Movie Region Update

    public LiveData<List<GenreResult>> updateGenresMoviesData() {
        if (genresMutableLiveData == null) {
            genresMutableLiveData = repository.updateGenresMoviesMutableLiveData();
        }
        return genresMutableLiveData;
    }

    public LiveData<MovieDetailsApiResponse> updateMovieDetails(int movieId, String language) {
        if (movieDetailsApiResponseMutableLiveData == null) {
            movieDetailsApiResponseMutableLiveData = repository.updateMovieDetailLiveData(movieId, language);
        }
        return movieDetailsApiResponseMutableLiveData;
    }

    public LiveData<VideosApiResponse> updateMovieVideos(int movieId, String language) {
        if (movieVideosApiResponseMutableLiveData == null) {
            movieVideosApiResponseMutableLiveData = repository.updateMovieVideosLiveData(movieId, language);
        }
        return movieVideosApiResponseMutableLiveData;
    }

    public LiveData<List<MovieResult>> updateRecommendations(int movieId, String language) {
        if (recommendationsApiResponseMutableLiveData == null) {
            recommendationsApiResponseMutableLiveData = repository.updateRecommendationsLiveData(movieId, language);
        }
        return recommendationsApiResponseMutableLiveData;
    }

    public LiveData<List<MovieResult>> updateSimilar(int movieId, String language) {
        if (similarApiResponseMutableLiveData == null) {
            similarApiResponseMutableLiveData = repository.updateSimilarLiveData(movieId, language);
        }
        return similarApiResponseMutableLiveData;
    }

    public LiveData<CastCrewApiResponse> updateCredits(int movieId, String language) {
        if (castCrewApiResponseMutableLiveData == null) {
            castCrewApiResponseMutableLiveData = repository.updateCreditsLiveData(movieId, language);
        }
        return castCrewApiResponseMutableLiveData;
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
