package com.best.movie.note.ui.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapter.NowPlayingMoviesAdapter;
import com.best.movie.note.adapter.PopularMoviesAdapter;
import com.best.movie.note.adapter.TopRatedMoviesAdapter;
import com.best.movie.note.adapter.TrendingMoviesAdapter;
import com.best.movie.note.adapter.UpcomingMoviesAdapter;
import com.best.movie.note.databinding.FragmentMoviesBinding;
import com.best.movie.note.model.movies.nowplaying.NowPlayingResult;
import com.best.movie.note.model.movies.popular.PopularResult;
import com.best.movie.note.model.movies.toprated.TopRatedResult;
import com.best.movie.note.model.movies.trending.TrendingResult;
import com.best.movie.note.model.movies.upcoming.UpcomingResult;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private FragmentMoviesBinding binding;

    // Popular Movies
    private ArrayList<PopularResult> popularResults;
    private RecyclerView popularMoviesRecyclerView;
    private PopularMoviesAdapter popularMoviesAdapter;
    // Playing Now Movies
    private ArrayList<NowPlayingResult> playingNowResults;
    private RecyclerView nowPlayingMoviesRecyclerView;
    private NowPlayingMoviesAdapter nowPlayingMoviesAdapter;
    // Trending Movies
    private ArrayList<TrendingResult> trendingResults;
    private RecyclerView trendingRecyclerView;
    private TrendingMoviesAdapter trendingMoviesAdapter;
    // Top Rated Movies
    private ArrayList<TopRatedResult> topRatedResults;
    private RecyclerView topRatedRecyclerView;
    private TopRatedMoviesAdapter topRatedMoviesAdapter;
    // Upcoming Movies
    private ArrayList<UpcomingResult> upcomingResults;
    private RecyclerView upcomingRecyclerView;
    private UpcomingMoviesAdapter upcomingMoviesAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//         binding = FragmentMoviesBinding.inflate(inflater, container, false);
//        //set variables in Binding
//        return binding.getRoot();
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_movies, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
//        binding.setMarsdata(data);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MoviesViewModel.class);

        getPopularMovies();
        getNowPlayingMovies();
        getTrendingMovies();
        getTopRatedMovies();
        getUpcomingMovies();

    }

    public void getPopularMovies() {
        moviesViewModel.getPopularMoviesData().observe(getActivity(),
                new Observer<List<PopularResult>>() {
                    @Override
                    public void onChanged(List<PopularResult> data) {
                        popularResults = (ArrayList<PopularResult>) data;
                        fillPopularRecyclerView();
                    }
                });
    }

    public void getNowPlayingMovies() {
        moviesViewModel.getNowPlayingMoviesData().observe(getActivity(),
                new Observer<List<NowPlayingResult>>() {
                    @Override
                    public void onChanged(List<NowPlayingResult> data) {
                        playingNowResults = (ArrayList<NowPlayingResult>) data;
                        fillPlayingNowRecyclerView();
                    }
                });
    }

    public void getTrendingMovies() {
        moviesViewModel.getTrendingMoviesData().observe(getActivity(),
                new Observer<List<TrendingResult>>() {
                    @Override
                    public void onChanged(List<TrendingResult> data) {
                        trendingResults = (ArrayList<TrendingResult>) data;

//                        Log.i("check", "trendingResults size:" + trendingResults.size());
//
//                        for (TrendingResult res : trendingResults) {
//                            Log.i("check", "Original title: " + res.getOriginalTitle());
//                        }

                        fillTrendingRecyclerView();
                    }
                });
    }

    public void getTopRatedMovies() {
        moviesViewModel.getTopRatedMoviesData().observe(getActivity(),
                new Observer<List<TopRatedResult>>() {
                    @Override
                    public void onChanged(List<TopRatedResult> data) {
                        topRatedResults = (ArrayList<TopRatedResult>) data;
                        fillTopRatedRecyclerView();
                    }
                });
    }

    public void getUpcomingMovies() {
        moviesViewModel.getUpcomingMoviesData().observe(getActivity(),
                new Observer<List<UpcomingResult>>() {
                    @Override
                    public void onChanged(List<UpcomingResult> data) {
                        upcomingResults = (ArrayList<UpcomingResult>) data;
                        fillUpcomingRecyclerView();
                    }
                });
    }

    private void fillPopularRecyclerView() {
        popularMoviesRecyclerView = binding.popularRecyclerView;
        popularMoviesAdapter = new PopularMoviesAdapter(popularResults);
        popularMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        popularMoviesRecyclerView.setAdapter(popularMoviesAdapter);
        popularMoviesAdapter.notifyDataSetChanged();
    }

    private void fillPlayingNowRecyclerView() {
        nowPlayingMoviesRecyclerView = binding.playingInTheatresRecyclerView;
        nowPlayingMoviesAdapter = new NowPlayingMoviesAdapter(playingNowResults);
        nowPlayingMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        nowPlayingMoviesRecyclerView.setAdapter(nowPlayingMoviesAdapter);
        nowPlayingMoviesAdapter.notifyDataSetChanged();
    }

    private void fillTrendingRecyclerView() {
        trendingRecyclerView = binding.trendingRecyclerView;
        trendingMoviesAdapter = new TrendingMoviesAdapter(trendingResults);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        trendingRecyclerView.setAdapter(trendingMoviesAdapter);
        trendingMoviesAdapter.notifyDataSetChanged();
    }

    private void fillTopRatedRecyclerView() {
        topRatedRecyclerView = binding.topRatedRecyclerView;
        topRatedMoviesAdapter = new TopRatedMoviesAdapter(topRatedResults);
        topRatedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.HORIZONTAL, false));
        topRatedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topRatedRecyclerView.setAdapter(topRatedMoviesAdapter);
        topRatedMoviesAdapter.notifyDataSetChanged();
    }

    private void fillUpcomingRecyclerView() {
        upcomingRecyclerView = binding.upComingRecyclerView;
        upcomingMoviesAdapter = new UpcomingMoviesAdapter(upcomingResults);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        upcomingRecyclerView.setAdapter(upcomingMoviesAdapter);
        upcomingMoviesAdapter.notifyDataSetChanged();
    }

}