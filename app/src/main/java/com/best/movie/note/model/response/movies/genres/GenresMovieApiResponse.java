package com.best.movie.note.model.response.movies.genres;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresMovieApiResponse {
    @SerializedName("genres")
    @Expose
    private List<GenreResult> genreResults = null;

    public List<GenreResult> getGenres() {
        return genreResults;
    }

    public void setGenres(List<GenreResult> genreResults) {
        this.genreResults = genreResults;
    }
}