package com.example.testproject.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testproject.data.net.Api;
import com.example.testproject.data.net.ApiService;
import com.example.testproject.data.room.MoviesDao;
import com.example.testproject.data.room.MoviesDataBase;
import com.example.testproject.data.room.model.Genres;
import com.example.testproject.data.room.model.MoviesModel;
import com.example.testproject.data.net.model.MovieData;
import com.example.testproject.data.net.model.MoviesData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository instance;
    private MoviesDao moviesDao;
    Api api;
    ApiService apiService;
    MutableLiveData<List<MovieData>> mutableLiveData = new MutableLiveData<>();

    public static MovieRepository getInstance(Application application) {
        if (instance == null)
            instance = new MovieRepository(application);
        return instance;
    }

    public MovieRepository(Application application) {
        MoviesDataBase moviesDataBase = MoviesDataBase.getInstance(application);
        moviesDao = moviesDataBase.setsDao();
        api = apiService.createHomeApiService().getRetrofit().create(Api.class);
    }

    public MutableLiveData<List<MovieData>> getMoviesData(boolean isExists) {

        api.getMovie(1).enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(Call<MoviesData> call, Response<MoviesData> response) {
                if(!isExists)
                insertMovies(response.body().data);
                mutableLiveData.postValue(response.body().data);
            }

            @Override
            public void onFailure(Call<MoviesData> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return mutableLiveData;
    }

    public LiveData<Boolean> isExists()
    {
        LiveData<Boolean> exists;
        exists=moviesDao.isExists();
        return exists;
    }

    public void insertMovies(List<MovieData> moviesModels) {
        new InsertMoviesAsyncTask(moviesDao).execute(moviesModels);
  }

    public LiveData<List<MoviesModel>> selectMovie() {
        LiveData<List<MoviesModel>> listLiveData;
        listLiveData = moviesDao.getAllMovies();
        return listLiveData;
    }

    public LiveData<List<Genres>> selectGenres() {
        LiveData<List<Genres>> listLiveData;
        listLiveData = moviesDao.getGenresAllById();
        return listLiveData;

    }

    private static class InsertMoviesAsyncTask extends AsyncTask<List<MovieData>, Void, Void> {
        private MoviesDao moviesDao;

        private InsertMoviesAsyncTask(MoviesDao moviesDao) {
            this.moviesDao = moviesDao;
        }

        @Override
        protected Void doInBackground(List<MovieData>... moviesModels) {
            List<MoviesModel> models = new ArrayList<>();
            List<Genres> genres = new ArrayList<>();
            int index=0;
            for (int i = 0; i < moviesModels[0].size(); i++) {
                MoviesModel model = new MoviesModel();
                model.setId(moviesModels[0].get(i).getId() - 1);
                model.setTitle(moviesModels[0].get(i).getTitle());
                model.setPoster(moviesModels[0].get(i).getPoster());
                models.add(model);
                for (int k = 0; k < moviesModels[0].get(i).getGenres().size(); k++) {
                    Genres gnrs = new Genres();
                    gnrs.setId_genres(index);
                    gnrs.setId(moviesModels[0].get(i).getId() - 1);
                    gnrs.setGenres_name(moviesModels[0].get(i).getGenres().get(k));
                    genres.add(gnrs);
                    index++;
                }
            }
            moviesDao.insertMovie(models);
            moviesDao.insertGenres(genres);
            return null;
        }
    }
}
