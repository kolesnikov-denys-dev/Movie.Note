package com.best.movie.note.ui.celebrities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.CommonContentAdapter;
import com.best.movie.note.databinding.CelebrityDetailsFragmentBinding;
import com.best.movie.note.databinding.FragmentCelebritiesBinding;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;
import com.best.movie.note.model.response.tvshows.persons.popular.PopularPersonApiResponse;
import com.best.movie.note.model.response.tvshows.persons.popular.Result;
import com.best.movie.note.model.response.tvshows.persons.trending.TrendingPersonApiResponse;

import java.util.ArrayList;

import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_MOVIE;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_TV_SHOW;

public class CelebritiesFragment extends Fragment implements CommonContentAdapter.OnMovieClickListener {

    private CelebritiesViewModel celebritiesViewModel;
    private FragmentCelebritiesBinding binding;
    private NavController navController;

    private PopularPersonApiResponse popularPersonResult;
    private TrendingPersonApiResponse trendingPersonResult;

    private RecyclerView popularOneRecyclerView;
    private CommonContentAdapter popularOneAdapter;
    private RecyclerView popularTwoRecyclerView;
    private CommonContentAdapter popularTwoAdapter;

    private RecyclerView trendingRecyclerView;
    private CommonContentAdapter trendingAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_celebrities, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        celebritiesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(CelebritiesViewModel.class);

        navController = Navigation.findNavController(view);

        getPopularPersons("en-US", "1");
        getTrendingPersons("en-US");
    }


    private void getPopularPersons(String language, String page) {
        celebritiesViewModel.getPopularPerson(language, page).observe(getActivity(),
                new Observer<PopularPersonApiResponse>() {
                    @Override
                    public void onChanged(PopularPersonApiResponse data) {
                        popularPersonResult = data;
                        fillPopularOneRecyclerView();
                        fillPopularTwoRecyclerView();
                    }
                });
    }

    public void getTrendingPersons(String language) {
        celebritiesViewModel.getTrendingPerson(language).observe(getActivity(),
                new Observer<TrendingPersonApiResponse>() {
                    @Override
                    public void onChanged(TrendingPersonApiResponse data) {
                        trendingPersonResult = data;
//                        fillTrendingPersonsRecyclerView();
                    }
                });
    }

    private void fillPopularOneRecyclerView() {
        // Convert Cast to MovieResult
        ArrayList<MovieResult> movies = new ArrayList<>();
        for (int i = 0; i < popularPersonResult.getResults().size(); i++) {
            MovieResult newMovie = new MovieResult();
            Result oldCast = popularPersonResult.getResults().get(i);
            newMovie.setOriginalTitle(oldCast.getName());
            newMovie.setId(oldCast.getId());
            newMovie.setPosterPath(oldCast.getProfilePath());
            // TODO HERE
            newMovie.setTitle(oldCast.getKnownForDepartment());
            movies.add(newMovie);
        }

        popularOneRecyclerView = binding.popularCelebritiesOneRecyclerView;
        popularOneAdapter = new CommonContentAdapter(movies, CARD_TYPE_VERTICAL, null, CONTENT_TYPE_MOVIE);
        popularOneRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularOneRecyclerView.setAdapter(popularOneAdapter);
        popularOneAdapter.setOnMovieClickListener(this);
        popularOneAdapter.notifyDataSetChanged();
    }


    public void fillPopularTwoRecyclerView() {
        // Convert Cast to MovieResult
        ArrayList<MovieResult> movies = new ArrayList<>();
        for (int i = 0; i < popularPersonResult.getResults().size(); i++) {
            MovieResult newMovie = new MovieResult();
            Result oldCast = popularPersonResult.getResults().get(i);
            newMovie.setOriginalTitle(oldCast.getName());
            newMovie.setId(oldCast.getId());
            newMovie.setPosterPath(oldCast.getProfilePath());
            // TODO HERE
            newMovie.setTitle(oldCast.getKnownForDepartment());
            movies.add(newMovie);
        }

        popularTwoRecyclerView = binding.popularCelebritiesTwoRecyclerView;
        popularTwoAdapter = new CommonContentAdapter(movies, CARD_TYPE_VERTICAL, null, CONTENT_TYPE_MOVIE);
        popularTwoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularTwoRecyclerView.setAdapter(popularTwoAdapter);
        popularTwoAdapter.setOnMovieClickListener(this);
        popularTwoAdapter.notifyDataSetChanged();
    }


    @Override
    public void onMovieClick(int contentId, String originalName, int contentType) {
        Bundle bundle = new Bundle();
        switch (contentType) {
            case CONTENT_TYPE_MOVIE: {
                Log.i("check", "was Clicked on :" + contentId);
                bundle.putInt("content_id", contentId);
                bundle.putInt("content_type", contentType);
                bundle.putString("original_name", originalName);
                navController.navigate(R.id.action_celebrityDetailsFragment_to_mainMovieFragment, bundle);
            }
            break;
            case CONTENT_TYPE_TV_SHOW: {
                bundle.putInt("content_id", contentId);
                bundle.putInt("content_type", contentType);
                bundle.putString("original_name", originalName);
                navController.navigate(R.id.action_celebrityDetailsFragment_to_mainMovieFragment, bundle);
            }
            break;
        }
    }


}