package com.best.movie.note.ui.common.list.movies.databinding;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.best.movie.note.R;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.service.ApiService;
import com.best.movie.note.service.ApiFactory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.best.movie.note.utils.Constants.API_KEY;


public class MovieDataSource extends PageKeyedDataSource<Long, MovieResult> {

    private Application application;
    private ApiService apiService;

    public MovieDataSource(ApiService apiService, Application application) {
        this.application = application;
        this.apiService = apiService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, MovieResult> callback) {
        Call<MoviesApiResponse> call = apiService.getPopularMoviesWithPaging(
                API_KEY,
                1);

        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                ArrayList<MovieResult> results = new ArrayList<>();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<MovieResult>) movieApiResponse.getResults();
                    callback.onResult(results, null, (long) 2);
                }

            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, MovieResult> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull final LoadCallback<Long, MovieResult> callback) {
        Call<MoviesApiResponse> call = apiService.getPopularMoviesWithPaging(
                API_KEY,
                params.key);

        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse movieApiResponse = response.body();
                ArrayList<MovieResult> results = new ArrayList<>();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<MovieResult>) movieApiResponse.getResults();
                    callback.onResult(results, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
    }

}
