package com.best.movie.note.model.movies.popular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMoviesApiResponse {

    @SerializedName("results")
    @Expose
    private List<PopularResult> results = null;

    public List<PopularResult> getResults() {
        return results;
    }

    public void setResults(List<PopularResult> results) {
        this.results = results;
    }
    // paging library
    public List<PopularResult> getPopularResults() {
        return results;
    }

}
