package com.best.movie.note.ui.favorite;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.CommonContentAdapter;
import com.best.movie.note.databinding.FragmentFavoritesBinding;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.tvshows.persons.popular.PopularPersonApiResponse;
import com.best.movie.note.model.response.tvshows.persons.popular.Result;
import com.best.movie.note.model.response.tvshows.persons.trending.TrendingPersonApiResponse;

import java.util.ArrayList;

import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_PERSON;

public class FavoritesFragment extends Fragment implements CommonContentAdapter.OnMovieClickListener {

    private FavoritesViewModel favoritesViewModel;
    private FragmentFavoritesBinding binding;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoritesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(FavoritesViewModel.class);

        navController = Navigation.findNavController(view);

        getTrendingPersons("en-US");
    }

    public void getTrendingPersons(String language) {
        favoritesViewModel.getTrendingPerson(language).observe(getActivity(),
                new Observer<TrendingPersonApiResponse>() {
                    @Override
                    public void onChanged(TrendingPersonApiResponse data) {
                        trendingPersonResult = data;

                    }
                });
    }

    private void fillPopularOneRecyclerView() {
        // Convert Cast to MovieResult
        ArrayList<MovieResult> movies = new ArrayList<>();
        for (int i = 0; i < popularPersonResult.getResults().size() / 2; i++) {
            MovieResult newMovie = new MovieResult();
            Result oldCast = popularPersonResult.getResults().get(i);
            newMovie.setOriginalTitle(oldCast.getName());
            newMovie.setId(oldCast.getId());
            newMovie.setPosterPath(oldCast.getProfilePath());
            movies.add(newMovie);
        }

        popularOneRecyclerView = binding.favoritesRecyclerView;
        popularOneAdapter = new CommonContentAdapter(movies, CARD_TYPE_VERTICAL, null, CONTENT_TYPE_PERSON);
        popularOneRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularOneRecyclerView.setAdapter(popularOneAdapter);
        popularOneAdapter.setOnMovieClickListener(this);
        popularOneAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int contentId, String originalName, int contentType) {
        Bundle bundle = new Bundle();
        switch (contentType) {
            case CONTENT_TYPE_PERSON: {
                bundle.putInt("cast_id", contentId);
                bundle.putString("cast_name", originalName);
//                navController.navigate(R.id.action_navigation_celebrities_to_celebrityDetailsFragment, bundle);
            }
            break;
        }
    }

    public class CelebritiesFragmentButtonsHandler {
        Bundle bundle = new Bundle();

        public void popularSeeAll(View view) {
            bundle.putString("what_open", getString(R.string.popular));
            navController.navigate(R.id.action_navigation_movies_to_navigation_movies_list, bundle);
        }

        public void nowPlayingSeeAll(View view) {
            bundle.putString("what_open", getString(R.string.playing_in_theathres));
            navController.navigate(R.id.action_navigation_movies_to_navigation_movies_list, bundle);
        }

    }

}