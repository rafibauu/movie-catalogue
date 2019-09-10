package com.example.moviecatalogue.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Parcelable {

    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/";

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String poster;

    @SerializedName("backdrop_path")
    @Expose
    private String background;

    @SerializedName("title")
    @Expose
    private String name;

    @SerializedName("overview")
    @Expose
    private String description;

    @SerializedName("original_language")
    @Expose
    private String language;

    @SerializedName("runtime")
    @Expose
    private int length;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("vote_average")
    @Expose
    private Double rating;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        String size = "w500";
        this.poster = IMAGE_PATH + size + poster;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        String size = "w500";
        this.background = IMAGE_PATH + size + background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getReleaseDate() {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat("dd, MMM yyyy", Locale.getDefault());

        try {
            parsed = df_input.parse(releaseDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.d("Date", "getReleaseDate: ");
        }

        return outputDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.background);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.language);
        dest.writeInt(this.length);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.rating);
        dest.writeInt(this.voteCount);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.background = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.language = in.readString();
        this.length = in.readInt();
        this.releaseDate = in.readString();
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = in.readInt();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
