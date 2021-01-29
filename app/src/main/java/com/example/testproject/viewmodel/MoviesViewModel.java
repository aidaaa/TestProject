package com.example.testproject.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testproject.data.net.model.MovieData;
import com.example.testproject.data.room.model.Genres;
import com.example.testproject.data.room.model.MoviesModel;
import com.example.testproject.repository.MovieRepository;

import java.util.List;

public class MoviesViewModel extends ViewModel {

    private LiveData<List<MoviesModel>> moviesLiveData;
    private LiveData<List<Genres>> genresLiveData;
    private MutableLiveData<List<MovieData>> moviesLiveDataNet;
    private LiveData<Boolean> exists;
    private MovieRepository repo;

    public void init(Application application) {
        if (moviesLiveData != null) {
            return;
        }
        repo = MovieRepository.getInstance(application);
    }

    public LiveData<Boolean> isExists() {
        exists = repo.isExists();
        return exists;
    }

    public LiveData<List<MoviesModel>> getAllMovies() {
        moviesLiveData = repo.selectMovie();
        return moviesLiveData;
    }


    public LiveData<List<Genres>> getAllGenres() {
        genresLiveData = repo.selectGenres();
        return genresLiveData;
    }

    public MutableLiveData<List<MovieData>> getMoviesLiveDataNet(boolean isExists) {
        moviesLiveDataNet = repo.getMoviesData(isExists);
        return moviesLiveDataNet;
    }
}
