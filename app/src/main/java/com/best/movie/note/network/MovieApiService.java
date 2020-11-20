package com.best.movie.note.network;


import com.best.movie.note.model.movies.nowplaying.PlayingNowMoviesApiResponse;
import com.best.movie.note.model.movies.popular.PopularMoviesApiResponse;
import com.best.movie.note.model.movies.trending.TrendingMoviesApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<PopularMoviesApiResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<PlayingNowMoviesApiResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/trending/movie/day")
    Call<TrendingMoviesApiResponse> getTrendingMovies(@Query("api_key") String apiKey);

    //  TODO Remove
    @GET("movie/popular")
    Call<PopularMoviesApiResponse> getPopularMoviesWithPaging(
            @Query("api_key") String apiKey, @Query("page") long page);
}
