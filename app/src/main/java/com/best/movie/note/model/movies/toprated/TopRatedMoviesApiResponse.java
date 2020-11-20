package com.best.movie.note.model.movies.toprated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopRatedMoviesApiResponse {

    @SerializedName("results")
    @Expose
    private List<TopRatedResult> topRatedResults = null;

    public List<TopRatedResult> getTopRatedResults() {
        return topRatedResults;
    }

    public void setTopRatedResults(List<TopRatedResult> topRatedResults) {
        this.topRatedResults = topRatedResults;
    }

}