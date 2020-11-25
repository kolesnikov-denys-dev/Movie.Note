package com.best.movie.note.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.service.ApiService;

import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;
import static com.best.movie.note.utils.Constants.TAG_ERROR;

import static com.best.movie.note.Global.getAppComponent;

public class MoviesDetailsRepository extends Application {

    private Application application;

    @Inject
    ApiService apiService;

    private CompositeDisposable compositeDisposable;

    public MoviesDetailsRepository(Application application) {
        this.application = application;
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectMoviesDetailsRepository(this);
    }

    // Genres Movies
    private ArrayList<GenreResult> genreResults;
    private final MutableLiveData<List<GenreResult>> genresMutableLiveData = new MutableLiveData<>();
    // Details Movie
    private MovieDetailsApiResponse movieDetailsResult;
    private final MutableLiveData<MovieDetailsApiResponse> movieDetailsApiResponseMutableLiveData = new MutableLiveData<>();
    // Videos Movie
    private VideosApiResponse videosResult;
    private final MutableLiveData<VideosApiResponse> movieVideosApiResponseMutableLiveData = new MutableLiveData<>();
    // Recommendations Movies
    private ArrayList<MovieResult> recommendationsResult;
    private final MutableLiveData<List<MovieResult>> recommendationsApiResponseMutableLiveData = new MutableLiveData<>();
    // Similar Movies
    private ArrayList<MovieResult> similarResult;
    private final MutableLiveData<List<MovieResult>> similarApiResponseMutableLiveData = new MutableLiveData<>();
    // Cast & Crew
    private CastCrewApiResponse castCrewResult;
    private final MutableLiveData<CastCrewApiResponse> castCrewApiResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<GenreResult>> getGenresMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getGenresMovies(API_KEY, QUERY_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenresMovieApiResponse>() {
                    @Override
                    public void accept(GenresMovieApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getGenres() != null) {
                            genreResults = (ArrayList<GenreResult>) moviesApiResponse.getGenres();
                            genresMutableLiveData.setValue(genreResults);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getUpcomingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return genresMutableLiveData;
    }

    public MutableLiveData<MovieDetailsApiResponse> getMovieDetailLiveData(int movieId, String language) {
        Call<MovieDetailsApiResponse> call = apiService.getMovieDetailsById(movieId, API_KEY, language);
        call.enqueue(new Callback<MovieDetailsApiResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsApiResponse> call, Response<MovieDetailsApiResponse> response) {
                MovieDetailsApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse != null) {
                    movieDetailsResult = moviesApiResponse;
                    movieDetailsApiResponseMutableLiveData.setValue(movieDetailsResult);
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getMovieDetailLiveData" + t.getLocalizedMessage());
            }
        });
        return movieDetailsApiResponseMutableLiveData;
    }

    public MutableLiveData<VideosApiResponse> getMovieVideosLiveData(int movieId, String language) {
        Call<VideosApiResponse> call = apiService.getMovieVideosById(movieId, API_KEY, language);
        call.enqueue(new Callback<VideosApiResponse>() {
            @Override
            public void onResponse(Call<VideosApiResponse> call, Response<VideosApiResponse> response) {
                VideosApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse != null) {
                    videosResult = moviesApiResponse;
                    movieVideosApiResponseMutableLiveData.setValue(videosResult);
                }
            }

            @Override
            public void onFailure(Call<VideosApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getMovieVideosLiveData" + t.getLocalizedMessage());
            }
        });
        return movieVideosApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getRecommendationsLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getRecommendationsById(movieId, API_KEY, language);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    recommendationsResult = (ArrayList<MovieResult>) movieApiResponse.getResults();
                    recommendationsApiResponseMutableLiveData.setValue(recommendationsResult);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getRecommendationsLiveData" + t.getLocalizedMessage());
            }
        });
        return recommendationsApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getSimilarLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getSimilarById(movieId, API_KEY, language);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    similarResult = (ArrayList<MovieResult>) movieApiResponse.getResults();
                    similarApiResponseMutableLiveData.setValue(similarResult);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getSimilarLiveData" + t.getLocalizedMessage());
            }
        });
        return similarApiResponseMutableLiveData;
    }

    public MutableLiveData<CastCrewApiResponse> getCreditsLiveData(int movieId, String language) {
        Call<CastCrewApiResponse> call = apiService.getCreditsById(movieId, API_KEY, language);
        call.enqueue(new Callback<CastCrewApiResponse>() {
            @Override
            public void onResponse(Call<CastCrewApiResponse> call, Response<CastCrewApiResponse> response) {
                CastCrewApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse != null) {
                    castCrewResult = movieApiResponse;
                    castCrewApiResponseMutableLiveData.setValue(castCrewResult);
                }
            }

            @Override
            public void onFailure(Call<CastCrewApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getCreditsLiveData" + t.getLocalizedMessage());
            }
        });
        return castCrewApiResponseMutableLiveData;
    }

}
