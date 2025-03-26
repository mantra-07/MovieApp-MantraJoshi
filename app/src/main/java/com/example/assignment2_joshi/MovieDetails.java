package com.example.assignment2_joshi;

import com.google.gson.annotations.SerializedName;

public class MovieDetails {

    @SerializedName("Title")
    private String title;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }
}
