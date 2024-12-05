package com.best.movie.note.ui.fragments.details.season;

import static com.best.movie.note.utils.Constants.QUERY_LANGUAGE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.EpisodesAdapter;
import com.best.movie.note.databinding.FragmentSeasonDetailsBinding;
import com.best.movie.note.model.response.tvshows.seasons.SeasonApiResponse;

public class SeasonDetailsFragment extends Fragment {
    private SeasonDetailsViewModel seasonDetailsViewModel;
    private FragmentSeasonDetailsBinding binding;
    private SeasonApiResponse seasonResult;
    private RecyclerView episodesRecyclerView;
    private EpisodesAdapter episodesAdapter;
    private int tvId;
    private String name;
    private int seasonNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_season_details, container, false);
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