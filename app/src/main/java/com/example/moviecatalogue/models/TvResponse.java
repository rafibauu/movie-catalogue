package com.example.moviecatalogue.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvResponse {

    @SerializedName("results")
    private List<Tv> tvs;

    public List<Tv> getTvs() {
        return tvs;
    }

}
