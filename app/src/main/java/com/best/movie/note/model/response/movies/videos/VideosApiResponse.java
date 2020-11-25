package com.best.movie.note.model.response.movies.videos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosApiResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideosResult> videosResults = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideosResult> getVideosResults() {
        return videosResults;
    }

    public void setVideosResults(List<VideosResult> videosResults) {
        this.videosResults = videosResults;
    }

}