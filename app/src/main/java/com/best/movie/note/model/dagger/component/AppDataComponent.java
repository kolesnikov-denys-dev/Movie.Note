package com.best.movie.note.model.dagger.component;

import com.best.movie.note.model.repositories.CelebritiesRepository;
import com.best.movie.note.model.repositories.CelebrityDetailsRepository;
import com.best.movie.note.model.repositories.MoviesDetailsRepository;
import com.best.movie.note.model.repositories.MoviesListRepository;
import com.best.movie.note.model.repositories.MoviesRepository;
import com.best.movie.note.model.repositories.TvShowsRepository;
import com.best.movie.note.model.dagger.module.DataModule;
import com.best.movie.note.ui.common.list.movies.MoviesListViewModel;


import dagger.Component;

@Component(modules = {DataModule.class})
public interface AppDataComponent {

    void injectMoviesDetailsRepository(MoviesDetailsRepository moviesDetailsRepository);

    void injectMoviesListRepository(MoviesListRepository moviesListRepository);

    void injectMoviesRepository(MoviesRepository moviesRepository);

    void injectMoviesListViewModel(MoviesListViewModel moviesListViewModel);

    void injectTvShowsViewModel(TvShowsRepository tvShowsRepository);

    void injectCelebrityDetailsRepository(CelebrityDetailsRepository celebrityDetailsRepository);

    void injectCelebritiesRepository(CelebritiesRepository celebritiesRepository);

}
