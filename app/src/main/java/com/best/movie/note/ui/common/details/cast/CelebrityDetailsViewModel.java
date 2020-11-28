package com.best.movie.note.ui.common.details.cast;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.best.movie.note.model.MoviesDetailsRepository;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;

import java.util.List;

public class CelebrityDetailsViewModel extends AndroidViewModel {

    private final MoviesDetailsRepository repository;

    public CelebrityDetailsViewModel(@NonNull Application application) {
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

    // End Region Tv Shows

}
