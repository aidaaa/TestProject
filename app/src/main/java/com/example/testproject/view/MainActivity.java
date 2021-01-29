package com.example.testproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.testproject.R;
import com.example.testproject.data.net.Api;
import com.example.testproject.data.net.ApiService;
import com.example.testproject.data.net.model.MovieData;
import com.example.testproject.data.net.model.MoviesData;
import com.example.testproject.data.room.model.Genres;
import com.example.testproject.data.room.model.MoviesModel;
import com.example.testproject.view.adapter.MoviesAdapter;
import com.example.testproject.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    RecyclerView rv;
    MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);

        boolean isConnected = isNetworkConnected();
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.init(getApplication());

        if (isConnected) {
            viewModel.isExists();
        }
        else
        {
            viewModel.getAllMovies();
            viewModel.getAllGenres();
        }

        viewModel.isExists().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                viewModel.getMoviesLiveDataNet(aBoolean).observe(MainActivity.this, new Observer<List<MovieData>>() {
                    @Override
                    public void onChanged(List<MovieData> movieData) {
                        List<MoviesModel> models = new ArrayList<>();
                        List<Genres> genres = new ArrayList<>();
                        for (int i = 0; i < movieData.size(); i++) {
                            MoviesModel movie = new MoviesModel();
                            movie.setTitle(movieData.get(i).getTitle());
                            movie.setPoster(movieData.get(i).getPoster());
                            movie.setId(movieData.get(i).getId());
                            for (int j = 0; j < movieData.get(i).getGenres().size(); j++) {
                                Genres g = new Genres();
                                g.setId(movieData.get(i).getId());
                                g.setGenres_name(movieData.get(i).getGenres().get(j));
                                genres.add(g);
                            }
                            models.add(movie);
                        }
                        adapter.setGenresList(genres);
                        adapter.setList(models);
                    }
                });

            }
        });

        viewModel.getAllMovies().observe(this, new Observer<List<MoviesModel>>() {
            @Override
            public void onChanged(List<MoviesModel> moviesModels) {
                adapter.setList(moviesModels);
            }
        });

        viewModel.getAllGenres().observe(this, new Observer<List<Genres>>() {
            @Override
            public void onChanged(List<Genres> genres) {
                adapter.setGenresList(genres);
            }
        });

        initRV();
    }

    private void initRV() {
        adapter = new MoviesAdapter(this);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setAdapter(adapter);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}