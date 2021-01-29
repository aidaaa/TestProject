package com.example.testproject.data.room.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "genres_table", foreignKeys = @ForeignKey(entity = MoviesModel.class,
        parentColumns = "id",
        childColumns = "id",
        onDelete = CASCADE))
public class Genres {
    @PrimaryKey(autoGenerate = false)
    private int id_genres;
    private int id;
    private String genres_name;

    public int getId_genres() {
        return id_genres;
    }

    public void setId_genres(int id_genres) {
        this.id_genres = id_genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenres_name() {
        return genres_name;
    }

    public void setGenres_name(String genres_name) {
        this.genres_name = genres_name;
    }
}
