package com.best.movie.note.ui.common.list.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.best.movie.note.model.MoviesRepository;
import com.best.movie.note.model.movies.list.MovieResult;
import com.best.movie.note.service.ApiService;
import com.best.movie.note.service.ApiFactory;
import com.best.movie.note.ui.common.list.movies.databinding.MovieDataSource;
import com.best.movie.note.ui.common.list.movies.databinding.MovieDataSourceFactory;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesListViewModel extends AndroidViewModel {

    private MoviesRepository movieRepository;
    private Executor executor;
    private LiveData<MovieDataSource> movieDataSourceLiveData;
    private LiveData<PagedList<MovieResult>> pagedListLiveData;
    private ApiService apiService;
    private ApiFactory apiFactory;

    public MoviesListViewModel(@NonNull Application application) {
        super(application);

        this.apiFactory = ApiFactory.getInstance();
        this.apiService = apiFactory.getApiService();

        movieRepository = new MoviesRepository(application);
        // DataSource
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(application, apiService);
        movieDataSourceLiveData = movieDataSourceFactory.getMutableLiveData();
        // PagedList
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(3)
                .build();

        executor = Executors.newCachedThreadPool();

        pagedListLiveData = new LivePagedListBuilder<Long, MovieResult>(movieDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<List<MovieResult>> getAllMovieData() {
        return movieRepository.getMutableLiveData();
    }

    public LiveData<PagedList<MovieResult>> getPagedListLiveData() {
        return pagedListLiveData;
    }

}
