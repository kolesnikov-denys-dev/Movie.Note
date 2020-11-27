package com.best.movie.note.model.dagger.component;

import com.best.movie.note.model.MoviesDetailsRepository;
import com.best.movie.note.model.MoviesListRepository;
import com.best.movie.note.model.MoviesRepository;
import com.best.movie.note.model.TvShowsRepository;
import com.best.movie.note.model.dagger.module.DataModule;
import com.best.movie.note.ui.common.list.movies.MoviesListViewModel;
import com.best.movie.note.ui.tvshows.TvShowsViewModel;


import dagger.Component;

@Component(modules = {DataModule.class})
public interface AppDataComponent {

    void injectMoviesDetailsRepository(MoviesDetailsRepository moviesDetailsRepository);

    void injectMoviesListRepository(MoviesListRepository moviesListRepository);

    void injectMoviesRepository(MoviesRepository moviesRepository);

    void injectMoviesListViewModel(MoviesListViewModel moviesListViewModel);

    void injectTvShowsViewModel(TvShowsRepository tvShowsRepository);

}
