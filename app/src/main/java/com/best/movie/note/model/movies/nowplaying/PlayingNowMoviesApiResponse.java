package com.best.movie.note.model.movies.nowplaying;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayingNowMoviesApiResponse {

    @SerializedName("results")
    @Expose
    private List<NowPlayingResult> nowPlayingResults = null;

    public List<NowPlayingResult> getNowPlayingResults() {
        return nowPlayingResults;
    }

    public void setNowPlayingResults(List<NowPlayingResult> nowPlayingResults) {
        this.nowPlayingResults = nowPlayingResults;
    }

}