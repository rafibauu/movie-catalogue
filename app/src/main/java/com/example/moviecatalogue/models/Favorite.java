package com.example.moviecatalogue.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite", indices = @Index(value = {"id"}, unique = true))
public class Favorite {

    @PrimaryKey
    private int id;

    private String itemKind;

    private String title;

    private String poster;

    private Double rating;

    public Favorite(int id, String itemKind, String title, String poster, Double rating) {
        this.id = id;
        this.itemKind = itemKind;
        this.title = title;
        this.poster = poster;
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getItemId() { return id; }

    public String getItemKind() {
        return itemKind;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public Double getRating() {
        return rating;
    }
}
