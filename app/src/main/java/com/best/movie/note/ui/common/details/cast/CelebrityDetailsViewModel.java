package com.best.movie.note.ui.common.details.cast;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.CelebrityDetailsRepository;
import com.best.movie.note.model.MoviesDetailsRepository;
import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;

import java.util.List;

public class CelebrityDetailsViewModel extends AndroidViewModel {

    private final CelebrityDetailsRepository repository;

    public CelebrityDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new CelebrityDetailsRepository(application);
    }

    public LiveData<CastDetailsApiResponse> getCastDetails(int castId, String language) {
        return repository.getCastDetailsMutableLiveData(castId, language);
    }

    public MutableLiveData<MoviesCastApiResponse> getCastMovies(int castId, String language) {
        return repository.getMoviesCastResultMutableLiveData(castId, language);
    }

    public MutableLiveData<MoviesCastApiResponse>  getCastTvShows(int castId, String language) {
        return repository.getTvShowsCatMutableLiveData(castId, language);
    }



    public LiveData<List<GenreResult>> getGenresMoviesData() {
        return repository.getGenresMoviesMutableLiveData();
    }


    public LiveData<List<GenreResult>> getTvShowsGenresMoviesData() {
        return repository.getGenresTvShowsMutableLiveData();
    }









}
