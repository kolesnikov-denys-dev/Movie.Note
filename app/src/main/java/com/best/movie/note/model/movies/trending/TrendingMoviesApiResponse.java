package com.best.movie.note.model.movies.trending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingMoviesApiResponse {

    @SerializedName("results")
    @Expose
    private List<TrendingResult> results = null;

    public List<TrendingResult> getResults() {
        return results;
    }

    public void setResults(List<TrendingResult> results) {
        this.results = results;
    }

}