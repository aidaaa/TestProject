package com.example.testproject.data.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private String base_url="http://moviesapi.ir/api/v1/";
    public static ApiService createHomeApiService()
    {
        return new ApiService();
    }

    public Retrofit getRetrofit(){
        OkHttpClient.Builder client=new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(base_url)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
