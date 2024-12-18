package com.best.movie.note.ui.fragments.movies;

import static com.best.movie.note.MovieApplication.getAppComponent;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.best.movie.note.model.repositories.MoviesListRepository;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.service.ApiService;
import com.best.movie.note.ui.fragments.movies.databinding.MovieDataSource;
import com.best.movie.note.ui.fragments.movies.databinding.MovieDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class MoviesListViewModel extends AndroidViewModel {
    private MoviesListRepository repository;
    private Executor executor;
    private LiveData<MovieDataSource> movieDataSourceLiveData;
    private LiveData<PagedList<MovieResult>> pagedListLiveData;
    private Application application;

    @Inject
    ApiService apiService;

    public MoviesListViewModel(Application application) {
        super(application);
        this.application = application;
        getAppComponent().injectMoviesListViewModel(this);
        repository = new MoviesListRepository(application);
    }

    public LiveData<PagedList<MovieResult>> getPagedListLiveData() {
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

        return pagedListLiveData;
    }
}