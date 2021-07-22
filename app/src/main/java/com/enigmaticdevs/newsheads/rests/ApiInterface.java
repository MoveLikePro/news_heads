package com.enigmaticdevs.newsheads.rests;

import com.enigmaticdevs.newsheads.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ApiInterface {
    @GET("top-headlines")
    Call<ResponseModel> getLatestNews(@Query("country") String country,@Query("category") String category, @Query("apiKey") String apiKey);
}
