package com.best.movie.note.ui.common.list.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.best.movie.note.model.MoviesRepository;
import com.best.movie.note.model.movies.cards.MovieResult;
import com.best.movie.note.network.MovieApiService;
import com.best.movie.note.network.RetrofitInstance;
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

    public MoviesListViewModel(@NonNull Application application) {
        super(application);

        // Где call.
        movieRepository = new MoviesRepository(application);
        MovieApiService movieApiService = RetrofitInstance.getService();
        // DataSource
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(application, movieApiService);
        movieDataSourceLiveData = movieDataSourceFactory.getMutableLiveData();
        // PagedList
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(3)
                .build();

        executor = Executors.newCachedThreadPool();

//        LivePagedListBuilder. Он будет создавать PagedList в отдельном потоке
//        Только вместо DataSource надо будет передавать DataSource.Factory
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
