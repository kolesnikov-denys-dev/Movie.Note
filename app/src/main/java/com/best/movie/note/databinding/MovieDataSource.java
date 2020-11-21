package com.best.movie.note.databinding;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;


import com.best.movie.note.R;
import com.best.movie.note.model.movies.cards.MovieResult;
import com.best.movie.note.model.movies.cards.MoviesApiResponse;
import com.best.movie.note.network.MovieApiService;
import com.best.movie.note.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 0 DiffUtil ItemCallBack Проверяет где новый обьект и какие поля обновились
// 1 RecyclerView.Adapter мы меняем на PagedListAdapter

// Вместо List у нас будет связка PagedList + DataSource,
// которая умеет по мере необходимости подтягивать данные из Storage.

// 2 PagingList обращается к DataSource за новой порцией данных, например, от 41 до 50.

// DataSource один из 3 типов: это посредник между PagedList и Storage.
//  Paging Library предоставляет три разных DataSource, которые должны нам помочь связать
//  между собой PagedList и Storage.
//  PositionalDataSource
//  PageKeyedDataSource +
//  ItemKeyedDataSource

//-------------------------------------------------------------------------------------------------

//    PageKeyedDataSource, Этот DataSource подходит для общения со Storage, который вместе с
//    очередной порцией данных передает нам какой-то ключ для получения следующей порции данных.
//
//    ItemKeyedDataSource, Но в качестве ключей он использует не отдельные значения типа page
//    или токена, а непосредственно данные.
//    --- Например, если вы выбираете цепочки комментариев для приложения
//    для обсуждения, вам может потребоваться передать идентификатор последнего комментария,
//    чтобы получить содержимое следующего комментария.

//    PositionalDataSource, Этот DataSource позволяет запрашивать данные по позиции. Т.е. если
//    тянем данные, например, из БД, то можем указать, с какой позиции и сколько данных грузить.
//    Если данные из файла, то указываем с какой строки и сколько строк грузить.

public class MovieDataSource extends PageKeyedDataSource<Long, MovieResult> {

    private Application application;
    private MovieApiService movieApiService;

    public MovieDataSource(MovieApiService movieApiService, Application application) {
        this.application = application;
        this.movieApiService = movieApiService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, MovieResult> callback) {
        movieApiService = RetrofitInstance.getService();
        Call<MoviesApiResponse> call = movieApiService.getPopularMoviesWithPaging(
                application.getApplicationContext().getString(R.string.api_key),
                1);

//        params  --- int requestedLoadSize - сколько данных грузить
//        boolean --- placeholdersEnabled - включены ли placeholders

        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse.getPopularResults() != null) {
                    callback.onResult((ArrayList<MovieResult>) moviesApiResponse.getPopularResults(), null, (long) 2);
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
        movieApiService = RetrofitInstance.getService();
        Call<MoviesApiResponse> call = movieApiService.getPopularMoviesWithPaging(
                application.getApplicationContext().getString(R.string.api_key),
                params.key);

        call.enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                MoviesApiResponse moviesApiResponse = response.body();
                if (moviesApiResponse != null && moviesApiResponse.getPopularResults() != null) {
                    callback.onResult(null, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {

            }
        });
    }

}
