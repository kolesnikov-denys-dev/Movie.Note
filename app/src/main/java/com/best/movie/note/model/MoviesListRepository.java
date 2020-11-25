package com.best.movie.note.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.best.movie.note.model.response.movies.cast.CastCrewApiResponse;
import com.best.movie.note.model.response.movies.details.MovieDetailsApiResponse;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.genres.GenresMovieApiResponse;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.movies.movie.MoviesApiResponse;
import com.best.movie.note.model.response.movies.videos.VideosApiResponse;
import com.best.movie.note.service.ApiFactory;
import com.best.movie.note.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.best.movie.note.utils.Constants.API_KEY;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;
import static com.best.movie.note.utils.Constants.TAG_ERROR;

public class MoviesListRepository {
    private Application application;
    private ApiFactory apiFactory;
    private ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public MoviesListRepository(Application application) {
        this.application = application;
        this.apiFactory = ApiFactory.getInstance();
        this.apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
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
