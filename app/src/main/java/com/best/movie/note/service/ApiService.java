package com.best.movie.note.service;


import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.model.response.tvshows.details.TvShowsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;

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

    @GET("trending/movie/week")
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


    // TvShows Region

    @GET("tv/airing_today")
    Observable<MoviesApiResponse> getAiringTodayTvShows(
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language,
            @Query(PARAMS_PAGE) String page);

    @GET("trending/tv/week")
    Observable<MoviesApiResponse> getTrendingTvShows(
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language,
            @Query(PARAMS_PAGE) String page);


    @GET("tv/top_rated")
    Observable<MoviesApiResponse> getTopRatedTvShows(
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language,
            @Query(PARAMS_PAGE) String page);

    @GET("tv/popular")
    Observable<MoviesApiResponse> getPopularTvShows(
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language,
            @Query(PARAMS_PAGE) String page);

    @GET("genre/tv/list")
    Observable<GenresMovieApiResponse> getGenresTvShows(
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


    // Movie Details Region

    @GET("tv/{tv_id}")
    Call<TvShowsApiResponse> getTvShowsDetailsById(
            @Path(PARAMS_TV_ID) int tvId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("tv/{tv_id}/videos")
    Call<VideosApiResponse> getTvShowsVideosById(
            @Path(PARAMS_TV_ID) int tvId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("tv/{tv_id}/recommendations")
    Call<MoviesApiResponse> getTvShowsRecommendationsById(
            @Path(PARAMS_TV_ID) int tvId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("tv/{tv_id}/similar")
    Call<MoviesApiResponse> getTvShowsSimilarById(
            @Path(PARAMS_TV_ID) int tvId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("tv/{tv_id}/credits")
    Call<CastCrewApiResponse> getTvShowsCreditsById(
            @Path(PARAMS_TV_ID) int tvId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    // End Region


    // Person / Cast Details Region

    @GET("person/{person_id}")
    Call<CastDetailsApiResponse> getCastDetailsById(
            @Path(PARAMS_PERSON_ID) int castId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("person/{person_id}/movie_credits")
    Call<MoviesCastApiResponse> getMoviesByCastId(
            @Path(PARAMS_PERSON_ID) int castId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    @GET("person/{person_id}/tv_credits")
    Call<TvShowsCatApiResponse> getTvShowsByCastId(
            @Path(PARAMS_PERSON_ID) int castId,
            @Query(PARAMS_API_KEY) String apiKey,
            @Query(PARAMS_LANGUAGE) String language);

    // End Region Person / Cast Details

}




