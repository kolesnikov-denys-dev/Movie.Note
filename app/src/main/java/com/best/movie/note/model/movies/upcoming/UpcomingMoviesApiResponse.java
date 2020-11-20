package com.best.movie.note.model.movies.upcoming;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingMoviesApiResponse {

    @SerializedName("results")
    @Expose
    private List<UpcomingResult> upcomingResults = null;


    public List<UpcomingResult> getUpcomingResults() {
        return upcomingResults;
    }

    public void setUpcomingResults(List<UpcomingResult> upcomingResults) {
        this.upcomingResults = upcomingResults;
    }

}