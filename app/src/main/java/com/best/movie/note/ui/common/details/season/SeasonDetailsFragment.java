package com.best.movie.note.ui.common.details.season;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.CastsAdapter;
import com.best.movie.note.adapters.CommonContentAdapter;
import com.best.movie.note.adapters.EpisodesAdapter;
import com.best.movie.note.databinding.CelebrityDetailsFragmentBinding;
import com.best.movie.note.databinding.SeasonDetailsFragmentBinding;
import com.best.movie.note.model.response.movies.genres.GenreResult;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.best.movie.note.model.response.tvshows.details.cast.CastDetailsApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.movie.Cast;
import com.best.movie.note.model.response.tvshows.details.cast.movie.MoviesCastApiResponse;
import com.best.movie.note.model.response.tvshows.details.cast.tvshows.TvShowsCatApiResponse;
import com.best.movie.note.model.response.tvshows.seasons.SeasonApiResponse;
import com.best.movie.note.ui.common.details.cast.CelebrityDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.best.movie.note.utils.Constants.CARD_TYPE_VERTICAL;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_MOVIE;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_TV_SHOW;
import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;

public class SeasonDetailsFragment extends Fragment {

    private SeasonDetailsViewModel seasonDetailsViewModel;
    private SeasonDetailsFragmentBinding binding;

    private SeasonApiResponse seasonResult;
    private RecyclerView episodesRecyclerView;
    private EpisodesAdapter episodesAdapter;

    private int tvId;
    private String name;
    private int seasonNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.season_details_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seasonDetailsViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(SeasonDetailsViewModel.class);

        if (getArguments() != null) {
            tvId = getArguments().getInt("tv_id");
            seasonNumber = getArguments().getInt("season_number");
            name = getArguments().getString("original_name");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(name);
            getEpisodes(tvId, seasonNumber, QUERY_LANGUAGE);
        }
    }

    private void getEpisodes(int tvId, int seasonNumber, String language) {
        seasonDetailsViewModel.getSeason(tvId, seasonNumber, language).observe(getActivity(),
                new Observer<SeasonApiResponse>() {
                    @Override
                    public void onChanged(SeasonApiResponse data) {
                        seasonResult = data;
                        fillEpisodesRecyclerView();
                    }
                });
    }

    private void fillEpisodesRecyclerView() {
        episodesRecyclerView = binding.episodesRecyclerView;
        episodesAdapter = new EpisodesAdapter(seasonResult.episodes);
        episodesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        episodesRecyclerView.setAdapter(episodesAdapter);
        episodesAdapter.notifyDataSetChanged();
    }

}