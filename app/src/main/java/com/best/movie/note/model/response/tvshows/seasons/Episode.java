package com.best.movie.note.model.response.tvshows.seasons;

import static com.best.movie.note.utils.Utils.setImage;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.util.List;

public class Episode{
    public String air_date;
    public int episode_number;
    public List<Crew> crew;
    public List<GuestStar> guest_stars;
    public int id;
    public String name;
    public String overview;
    public String production_code;
    public int season_number;
    public String still_path;
    public Double vote_average;
    public int vote_count;

    @BindingAdapter({"posterPath"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        setImage(imageView, imageUrl);
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public List<GuestStar> getGuest_stars() {
        return guest_stars;
    }

    public void setGuest_stars(List<GuestStar> guest_stars) {
        this.guest_stars = guest_stars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getProduction_code() {
        return production_code;
    }

    public void setProduction_code(String production_code) {
        this.production_code = production_code;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getStill_path() {
        return still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}