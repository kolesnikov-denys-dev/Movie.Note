package com.best.movie.note.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.best.movie.note.R;
import com.best.movie.note.model.genres.GenreResult;
import com.best.movie.note.model.genres.GenresMovieApiResponse;
import com.best.movie.note.model.movies.list.MovieResult;
import com.best.movie.note.model.movies.list.MoviesApiResponse;
import com.best.movie.note.model.movies.main.details.MovieDetailsApiResponse;
import com.best.movie.note.model.movies.main.recommended.RecommendationsApiResponse;
import com.best.movie.note.model.movies.main.videos.VideosApiResponse;
import com.best.movie.note.service.ApiService;
import com.best.movie.note.service.ApiFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    private Application application;
    private ApiFactory apiFactory;
    private ApiService apiService;

    public MoviesRepository(Application application) {
        this.application = application;
        this.apiFactory = ApiFactory.getInstance();
        this.apiService = apiFactory.getApiService();
    }

    // Popular Movies
    private ArrayList<MovieResult> movieResults;
    private final MutableLiveData<List<MovieResult>> mutableLiveData = new MutableLiveData<>();
    // Playing Now Movies
    private ArrayList<MovieResult> nowPlayingResults;
    private final MutableLiveData<List<MovieResult>> playingNowMutableLiveData = new MutableLiveData<>();
    // Trending Movies
    private ArrayList<MovieResult> trendingResults;
    private final MutableLiveData<List<MovieResult>> trendingMutableLiveData = new MutableLiveData<>();
    // Top Rated Movies
    private ArrayList<MovieResult> topRatedResults;
    private final MutableLiveData<List<MovieResult>> topRatedMutableLiveData = new MutableLiveData<>();
    // Upcoming Movies
    private ArrayList<MovieResult> upcomingResults;
    private final MutableLiveData<List<MovieResult>> upcomingMutableLiveData = new MutableLiveData<>();
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


    // Paging Library
    private ArrayList<MovieResult> results = new ArrayList<>();
    private MutableLiveData<List<MovieResult>> mutablePagingLiveData = new MutableLiveData<>();

    public MutableLiveData<List<MovieResult>> getMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getPopularMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<MovieResult>) movieApiResponse.getResults();
                    mutablePagingLiveData.setValue(results);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
        return mutablePagingLiveData;
    }

    public MutableLiveData<List<MovieResult>> getPopularMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getPopularMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse.getPopularResults() != null) {
                    movieResults = (ArrayList<MovieResult>) moviesApiResponse.getPopularResults();
                    mutableLiveData.setValue(movieResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getNowPlayingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getNowPlayingMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse playingNowMoviesApiResponse = response.body();
                if (playingNowMoviesApiResponse != null && playingNowMoviesApiResponse.getPopularResults() != null) {
                    nowPlayingResults = (ArrayList<MovieResult>) playingNowMoviesApiResponse.getPopularResults();
                    playingNowMutableLiveData.setValue(nowPlayingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
        return playingNowMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTrendingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getTrendingMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getPopularResults() != null) {
                    trendingResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getPopularResults();
                    trendingMutableLiveData.setValue(trendingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.i("check", "ERROR: ");
            }
        });
        return trendingMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTopRatedMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService.getTopRatedMovies(application.getApplicationContext()
                .getString(R.string.api_key));
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getPopularResults() != null) {
                    topRatedResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getPopularResults();
                    topRatedMutableLiveData.setValue(topRatedResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
            }
        });
        return topRatedMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getUpcomingMoviesMutableLiveData() {
        Call<MoviesApiResponse> call = apiService
                .getUpcomingMovies(application.getApplicationContext()
                                .getString(R.string.api_key),
                        "en-US",
                        "1");
        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse trendingMoviesApiResponse = response.body();
                Log.d("check", "getUpcomingMoviesMutableLiveData : " + response.toString());
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getPopularResults() != null) {
                    upcomingResults = (ArrayList<MovieResult>) trendingMoviesApiResponse.getPopularResults();
                    upcomingMutableLiveData.setValue(upcomingResults);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                Log.d("check", "onFailure : " + t.getLocalizedMessage());
            }
        });
        return upcomingMutableLiveData;
    }

    public MutableLiveData<List<GenreResult>> getGenresMoviesMutableLiveData() {
        Call<GenresMovieApiResponse> call = apiService.getGenresMovies(application.getApplicationContext()
                        .getString(R.string.api_key),
                "en-US");
        call.enqueue(new Callback<GenresMovieApiResponse>() {
            @Override
            public void onResponse(Call<GenresMovieApiResponse> call, Response<GenresMovieApiResponse> response) {
                GenresMovieApiResponse trendingMoviesApiResponse = response.body();
                Log.d("check", "getUpcomingMoviesMutableLiveData : " + response.toString());
                if (trendingMoviesApiResponse != null && trendingMoviesApiResponse.getGenres() != null) {
                    genreResults = (ArrayList<GenreResult>) trendingMoviesApiResponse.getGenres();
                    genresMutableLiveData.setValue(genreResults);
                }
            }

            @Override
            public void onFailure(Call<GenresMovieApiResponse> call, Throwable t) {
                Log.d("check", "onFailure : " + t.getLocalizedMessage());
            }
        });
        return genresMutableLiveData;
    }

    public MutableLiveData<MovieDetailsApiResponse> getMovieDetailLiveData(int movieId, String language) {
        Call<MovieDetailsApiResponse> call = apiService.getMovieDetailsById(movieId, application.getApplicationContext()
                .getString(R.string.api_key), language);
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
                Log.e("check", t.getLocalizedMessage());
            }
        });
        return movieDetailsApiResponseMutableLiveData;
    }

    public MutableLiveData<VideosApiResponse> getMovieVideosLiveData(int movieId, String language) {
        Call<VideosApiResponse> call = apiService.getMovieVideosById(movieId, application.getApplicationContext()
                .getString(R.string.api_key), language);
        call.enqueue(new Callback<VideosApiResponse>() {
            @Override
            public void onResponse(Call<VideosApiResponse> call, Response<VideosApiResponse> response) {
                VideosApiResponse moviesApiResponse = response.body();
                Log.i("check", "----------WORK!!!" + moviesApiResponse.toString());
                if (moviesApiResponse != null && moviesApiResponse != null) {
                    videosResult = moviesApiResponse;
                    movieVideosApiResponseMutableLiveData.setValue(videosResult);
                }
            }

            @Override
            public void onFailure(Call<VideosApiResponse> call, Throwable t) {
                Log.e("check", t.getLocalizedMessage());
            }
        });
        return movieVideosApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getRecommendationsLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getRecommendationsById(movieId, application.getApplicationContext()
                .getString(R.string.api_key), language);
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

            }
        });
        return recommendationsApiResponseMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getSimilarLiveData(int movieId, String language) {
        Call<MoviesApiResponse> call = apiService.getSimilarById(movieId, application.getApplicationContext()
                .getString(R.string.api_key), language);
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

            }
        });
        return similarApiResponseMutableLiveData;
    }

}
