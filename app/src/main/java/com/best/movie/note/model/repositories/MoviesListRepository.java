package com.best.movie.note.model.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.best.movie.note.Global.getAppComponent;

public class MoviesListRepository {
    private Application application;
    @Inject
    ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public MoviesListRepository(Application application) {
        this.application = application;
        compositeDisposable = new CompositeDisposable();
        getAppComponent().injectMoviesListRepository(this);
    }

    // Popular Movies
    private ArrayList<MovieResult> movieResults;
    private final List<MovieResult> mutableLiveDate = new ArrayList<>();
    // Playing Now Movies
    private ArrayList<MovieResult> nowPlayingResults;
    private final MutableLiveData<List<MovieResult>> playingNowMutableLiveData = new MutableLiveData<>();
    // Trending Movies
    private ArrayList<MovieResult> trendingResults;
    private final MutableLiveData<List<MovieResult>> trendingMutableLiveData = new MutableLiveData<>();
    // Top Rated Movies
    private ArrayList<MovieResult> topRatedResults;
    private final MutableLiveData<List<MovieResult>> topRatedMutableLiveData = new MutableLiveData<>();
    // Upcoming Movies
    private ArrayList<MovieResult> upcomingResults;
    private final MutableLiveData<List<MovieResult>> upcomingMutableLiveData = new MutableLiveData<>();
    // Genres Movies
    private ArrayList<GenreResult> genreResults;
    private final MutableLiveData<List<GenreResult>> genresMutableLiveData = new MutableLiveData<>();
    // Paging Library
    private ArrayList<MovieResult> results = new ArrayList<>();
    private MutableLiveData<List<MovieResult>> mutablePagingLiveData = new MutableLiveData<>();

//    public MutableLiveData<List<GenreResult>> getGenresMoviesMutableLiveData() {
//        Disposable disposableSimpleData = apiService.getGenresMovies(API_KEY, QUERY_LANGUAGE)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<GenresMovieApiResponse>() {
//                    @Override
//                    public void accept(GenresMovieApiResponse moviesApiResponse) throws Exception {
//                        if (moviesApiResponse != null && moviesApiResponse.getGenres() != null) {
//                            genreResults = (ArrayList<GenreResult>) moviesApiResponse.getGenres();
//                            genresMutableLiveData.setValue(genreResults);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.e(TAG_ERROR, "onFailure: getUpcomingMoviesMutableLiveData" + throwable.getLocalizedMessage());
//                    }
//                });
//        compositeDisposable.add(disposableSimpleData);
//        return genresMutableLiveData;
//    }

}
