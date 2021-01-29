package com.example.testproject.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testproject.data.room.model.Genres;
import com.example.testproject.data.room.model.MoviesModel;

@Database(entities = {MoviesModel.class, Genres.class},version = 1)
public abstract class MoviesDataBase extends RoomDatabase {
    private static MoviesDataBase instance;

    public abstract MoviesDao setsDao();

    public static synchronized MoviesDataBase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDataBase.class,"movies_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
