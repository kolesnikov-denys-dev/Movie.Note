package com.best.movie.note.model.response.movies.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesApiResponse {
    @SerializedName("results")
    @Expose
    private List<MovieResult> results = null;

    public List<MovieResult> getResults() {
        return results;
    }

    public void setResults(List<MovieResult> results) {
        this.results = results;
    }
}
