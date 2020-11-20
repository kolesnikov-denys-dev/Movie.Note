package com.best.movie.note.model.movies.popular;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMoviesApiResponse implements Parcelable {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<PopularResult> popularResults = null;
    public final static Creator<PopularMoviesApiResponse> CREATOR = new Creator<PopularMoviesApiResponse>() {
        @SuppressWarnings({
                "unchecked"
        })
        public PopularMoviesApiResponse createFromParcel(Parcel in) {
            return new PopularMoviesApiResponse(in);
        }
        public PopularMoviesApiResponse[] newArray(int size) {
            return (new PopularMoviesApiResponse[size]);
        }
    };

    protected PopularMoviesApiResponse(Parcel in) {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.popularResults, (PopularResult.class.getClassLoader()));
    }

    public PopularMoviesApiResponse() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<PopularResult> getPopularResults() {
        return popularResults;
    }

    public void setPopularResults(List<PopularResult> popularResults) {
        this.popularResults = popularResults;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(popularResults);
    }

    public int describeContents() {
        return 0;
    }

}
