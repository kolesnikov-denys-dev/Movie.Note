package com.best.movie.note.service;


import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.best.movie.note.utils.Constants.*;

public interface ApiService {

    // Movies Region

    @GET("movie/popular")
    Observable<MoviesApiResponse> getPopularMovies(@Query(PARAMS_API_KEY) String apiKey);

    @GET("movie/popular")
    Observable<MoviesApiResponse> getPopularMoviesWithPaging(
            @Query(PARAMS_API_KEY) String apiKey, @Query(PARAMS_PAGE) long page);

    @GET("movie/now_playing")
    Observable<MoviesApiResponse> getNowPlayingMovies(@Query(PARAMS_API_KEY) String apiKey);

    @GET("trending/movie/day")
    Observable<MoviesApiResponse> getTrendingMovies(@Query(PARAMS_API_KEY) String apiKey);

    @GET("movie/top_rated")
    Observable<MoviesApiResponse> getTopRatedMovies(@Query(PARAMS_API_KEY) String apiKey);

    @GET("movie/upcoming")
    Observable<MoviesApiResponse> getUpcomingMovies(
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language,
            @Query(PARAMS_PAGE) String page);

    @GET("genre/movie/list")
    Observable<GenresMovieApiResponse> getGenresMovies(
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    // End Region


    // Movie Details Region

    @GET("movie/{movie_id}")
    Call<MovieDetailsApiResponse> getMovieDetailsById(
            @Path(PARAMS_MOVIE_ID) int movieId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("movie/{movie_id}/videos")
    Call<VideosApiResponse> getMovieVideosById(
            @Path(PARAMS_MOVIE_ID) int movieId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("movie/{movie_id}/recommendations")
    Call<MoviesApiResponse> getRecommendationsById(
            @Path(PARAMS_MOVIE_ID) int movieId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("movie/{movie_id}/similar")
    Call<MoviesApiResponse> getSimilarById(
            @Path(PARAMS_MOVIE_ID) int movieId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("movie/{movie_id}/credits")
    Call<CastCrewApiResponse> getCreditsById(
            @Path(PARAMS_MOVIE_ID) int movieId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    // End Region


}




