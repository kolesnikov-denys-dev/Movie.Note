package com.best.movie.note.network;


import com.best.movie.note.model.genres.GenresMovieApiResponse;
import com.best.movie.note.model.movies.cards.MoviesApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MoviesApiResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesApiResponse> getPopularMoviesWithPaging(
            @Query("api_key") String apiKey, @Query("page") long page);

    @GET("movie/now_playing")
    Call<MoviesApiResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("trending/movie/day")
    Call<MoviesApiResponse> getTrendingMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesApiResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesApiResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page);


    @GET("genre/movie/list")
    Call<GenresMovieApiResponse> getGenresMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language);

}




