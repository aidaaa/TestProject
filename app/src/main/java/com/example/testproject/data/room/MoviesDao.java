package com.example.testproject.data.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.testproject.data.room.model.Genres;
import com.example.testproject.data.room.model.MoviesModel;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert
    void insertMovie(List<MoviesModel> movieData);

    @Insert
    void insertGenres(List<Genres> genres);

    @Query("SELECT * FROM movies_table")
    LiveData<List<MoviesModel>> getAllMovies();

    @Query("SELECT * FROM genres_table")
    LiveData<List<Genres>> getGenresAllById();

    @Query("SELECT EXISTS(SELECT * FROM movies_table)")
    LiveData<Boolean> isExists();

    @Query("SELECT * FROM movies_table")
    List<MoviesModel> getMovieByRX();
}
