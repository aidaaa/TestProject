package com.example.testproject.data.net;

import com.example.testproject.data.net.model.MoviesData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api
{
    @GET("movies")
    Call<MoviesData> getMovie(@Query("page") Integer page);
}
