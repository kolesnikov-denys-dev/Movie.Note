package com.best.movie.note.ui.common.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapter.MoviesAdapter;
import com.best.movie.note.databinding.MovieDetailsFragmentBinding;
import com.best.movie.note.model.movies.main.details.MovieDetailsApiResponse;
import com.best.movie.note.model.movies.main.videos.VideosApiResponse;

public class MovieDetailsFragment extends Fragment {

    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsFragmentBinding binding;
    private NavController navController;

    private VideosApiResponse videosResult;

    // Popular Movies
    private MovieDetailsApiResponse movieDetailsResult;
    private RecyclerView popularMoviesRecyclerView;
    private MoviesAdapter moviesAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false);
        binding.setButtonHandler(new MovieDetailsFragmentButtonsHandler());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieDetailsViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MovieDetailsViewModel.class);

        navController = Navigation.findNavController(view);
//        binding.setButtonHandler(new MoviesFragment.MoviesFragmentButtonsHandler());

        if (getArguments() != null) {
            int movieId = getArguments().getInt("movie_id");
            String originalName = getArguments().getString("original_name");
            Toast.makeText(getContext(), "Movie Id: " + movieId, Toast.LENGTH_SHORT).show();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(originalName);
            getMovieDetail(movieId, "en-US");
            getMovieVideos(movieId, "en-US");
        }

    }


    public void getMovieDetail(int movieId, String language) {
        movieDetailsViewModel.getMovieDetails(movieId, language).observe(getActivity(),
                new Observer<MovieDetailsApiResponse>() {
                    @Override
                    public void onChanged(MovieDetailsApiResponse data) {
                        binding.setMovieDetailsResult(data);
                        movieDetailsResult = data;
                        fillView();
                    }
                });
    }


    public void getMovieVideos(int movieId, String language) {
        movieDetailsViewModel.getMovieVideos(movieId, language).observe(getActivity(),
                new Observer<VideosApiResponse>() {
                    @Override
                    public void onChanged(VideosApiResponse data) {
                        videosResult = data;
                    }
                });
    }

    private void fillView() {
//        popularMoviesRecyclerView = binding.popularRecyclerView;
//        moviesAdapter = new MoviesAdapter(movieResults, 99, genresResults);
//        popularMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        popularMoviesRecyclerView.setAdapter(moviesAdapter);
//        moviesAdapter.setOnItemClickListener(this);
//        moviesAdapter.notifyDataSetChanged();
    }

    public class MovieDetailsFragmentButtonsHandler {
        //TODO if not trailer
        public void showTrailer(View view) {
            if (videosResult != null &&
                    videosResult.getVideosResults() != null &&
                    videosResult.getVideosResults().get(0) != null &&
                    videosResult.getVideosResults().get(0).getKey() != null) {
                String id = videosResult.getVideosResults().get(0).getKey();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            } else {
                Toast.makeText(getContext(), "Not trailer", Toast.LENGTH_SHORT).show();
            }
        }

        public void share(View view) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            String message = "Create notes your movies with the application \"Movie Note\":";
            message += "\n\n" + movieDetailsResult.getOriginalTitle();
            message += "\n\n " + movieDetailsResult.getOverview();

            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

}