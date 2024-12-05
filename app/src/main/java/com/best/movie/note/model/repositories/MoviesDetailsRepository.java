package com.best.movie.note.model.repositories;

import static com.best.movie.note.MovieApplication.getAppComponent;
import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;
import static com.best.movie.note.utils.Constants.TAG_ERROR;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;
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

public class MoviesDetailsRepository {
    @Inject
    ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public MoviesDetailsRepository() {
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectMoviesDetailsRepository(this);
    }

    // Movies Region
    private MutableLiveData<List<GenreResult>> genresMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<MovieDetailsApiResponse> movieDetailsApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<VideosApiResponse> movieVideosApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> recommendationsApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> similarApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CastCrewApiResponse> castCrewApiResponseMutableLiveData = new MutableLiveData<>();
    // End Region Movies

    // Tv Shows Region
    private MutableLiveData<List<GenreResult>> tvShowGenresMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<TvShowsApiResponse> tvShowDetailsApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<VideosApiResponse> tvShowVideosApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> tvShowRecommendationsApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> tvShowSimilarApiResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CastCrewApiResponse> tvShowCastCrewApiResponseMutableLiveData = new MutableLiveData<>();
    // End Region

    // Movies Region
    public MutableLiveData<List<GenreResult>> updateGenresMoviesMutableLiveData() {
        Disposable disposableSimpleData = apiService.getGenresMovies(API_KEY, QUERY_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenresMovieApiResponse>() {
                    @Override
                    public void accept(GenresMovieApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getGenres() != null) {
                            genresMutableLiveData.setValue((ArrayList<GenreResult>) moviesApiResponse.getGenres());
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

    public MutableLiveData<MovieDetailsApiResponse> updateMovieDetailLiveData(int movieId, String language) {
        Call<MovieDetailsApiResponse> call = apiService.getMovieDetailsById(movieId, API_KEY, language);
        call.enqueue(new Callback<MovieDetailsApiResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsApiResponse> call, Response<MovieDetailsApiResponse> response) {
                MovieDetailsApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse != null) {
                    movieDetailsApiResponseMutableLiveData.setValue(moviesApiResponse);
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getMovieDetailLiveData" + t.getLocalizedMessage());
            }
        });
        return movieDetailsApiResponseMutableLiveData;
    }

    public MutableLiveData<VideosApiResponse> updateMovieVideosLiveData(int movieId, String language) {
        Call<VideosApiResponse> call = apiService.getMovieVideosById(movieId, API_KEY, language);
        call.enqueue(new Callback<VideosApiResponse>() {
            @Override
            public void onResponse(Call<VideosApiResponse> call, Response<VideosApiResponse> response) {
                VideosApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse != null) {
                    movieVideosApiResponseMutableLiveData.setValue(moviesApiResponse);
                }
            }

            @Override
            public void onFailure(Call<VideosApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getMovieVideosLiveData" + t.getLocalizedMessage());
            }
        });
        return movieVideosApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> updateRecommendationsLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getRecommendationsById(movieId, API_KEY, language);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    recommendationsApiResponseMutableLiveData.setValue((ArrayList<MovieResult>) movieApiResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getRecommendationsLiveData" + t.getLocalizedMessage());
            }
        });
        return recommendationsApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> updateSimilarLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getSimilarById(movieId, API_KEY, language);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    similarApiResponseMutableLiveData.setValue((ArrayList<MovieResult>) movieApiResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getSimilarLiveData" + t.getLocalizedMessage());
            }
        });
        return similarApiResponseMutableLiveData;
    }

    public MutableLiveData<CastCrewApiResponse> updateCreditsLiveData(int movieId, String language) {
        Call<CastCrewApiResponse> call = apiService.getCreditsById(movieId, API_KEY, language);
        call.enqueue(new Callback<CastCrewApiResponse>() {
            @Override
            public void onResponse(Call<CastCrewApiResponse> call, Response<CastCrewApiResponse> response) {
                CastCrewApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse != null) {
                    castCrewApiResponseMutableLiveData.setValue(movieApiResponse);
                }
            }

            @Override
            public void onFailure(Call<CastCrewApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getCreditsLiveData" + t.getLocalizedMessage());
            }
        });
        return castCrewApiResponseMutableLiveData;
    }
    // End Region Movies

    // Tv Shows Region
    public MutableLiveData<List<GenreResult>> getGenresTvShowsMutableLiveData() {
        Disposable disposableSimpleData = apiService.getGenresTvShows(API_KEY, QUERY_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenresMovieApiResponse>() {
                    @Override
                    public void accept(GenresMovieApiResponse moviesApiResponse) throws Exception {
                        if (moviesApiResponse != null && moviesApiResponse.getGenres() != null) {
                            tvShowGenresMutableLiveData.setValue((ArrayList<GenreResult>) moviesApiResponse.getGenres());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG_ERROR, "onFailure: getUpcomingMoviesMutableLiveData" + throwable.getLocalizedMessage());
                    }
                });
        compositeDisposable.add(disposableSimpleData);
        return tvShowGenresMutableLiveData;
    }

    public MutableLiveData<TvShowsApiResponse> getTvShowsDetailLiveData(int movieId, String language) {
        Call<TvShowsApiResponse> call = apiService.getTvShowsDetailsById(movieId, API_KEY, language);
        call.enqueue(new Callback<TvShowsApiResponse>() {
            @Override
            public void onResponse(Call<TvShowsApiResponse> call, Response<TvShowsApiResponse> response) {
                TvShowsApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse != null) {
                    tvShowDetailsApiResponseMutableLiveData.setValue(moviesApiResponse);
                }
            }

            @Override
            public void onFailure(Call<TvShowsApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getMovieDetailLiveData" + t.getLocalizedMessage());
            }
        });
        return tvShowDetailsApiResponseMutableLiveData;
    }

    public MutableLiveData<VideosApiResponse> getTvShowsVideosLiveData(int movieId, String language) {
        Call<VideosApiResponse> call = apiService.getTvShowsVideosById(movieId, API_KEY, language);
        call.enqueue(new Callback<VideosApiResponse>() {
            @Override
            public void onResponse(Call<VideosApiResponse> call, Response<VideosApiResponse> response) {
                VideosApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse != null) {
                    tvShowVideosApiResponseMutableLiveData.setValue(moviesApiResponse);
                }
            }

            @Override
            public void onFailure(Call<VideosApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getMovieVideosLiveData" + t.getLocalizedMessage());
            }
        });
        return tvShowVideosApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTvShowsRecommendationsLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getTvShowsRecommendationsById(movieId, API_KEY, language);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    tvShowRecommendationsApiResponseMutableLiveData.setValue((ArrayList<MovieResult>) movieApiResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getRecommendationsLiveData" + t.getLocalizedMessage());
            }
        });
        return tvShowRecommendationsApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTvShowsSimilarLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getTvShowsSimilarById(movieId, API_KEY, language);
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    tvShowSimilarApiResponseMutableLiveData.setValue((ArrayList<MovieResult>) movieApiResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getSimilarLiveData" + t.getLocalizedMessage());
            }
        });
        return tvShowSimilarApiResponseMutableLiveData;
    }

    public MutableLiveData<CastCrewApiResponse> getTvShowsCreditsLiveData(int movieId, String language) {
        Call<CastCrewApiResponse> call = apiService.getTvShowsCreditsById(movieId, API_KEY, language);
        call.enqueue(new Callback<CastCrewApiResponse>() {
            @Override
            public void onResponse(Call<CastCrewApiResponse> call, Response<CastCrewApiResponse> response) {
                CastCrewApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse != null) {
                    tvShowCastCrewApiResponseMutableLiveData.setValue(movieApiResponse);
                }
            }

            @Override
            public void onFailure(Call<CastCrewApiResponse> call, Throwable t) {
                Log.e(TAG_ERROR, "onFailure: getCreditsLiveData" + t.getLocalizedMessage());
            }
        });
        return tvShowCastCrewApiResponseMutableLiveData;
    }
}