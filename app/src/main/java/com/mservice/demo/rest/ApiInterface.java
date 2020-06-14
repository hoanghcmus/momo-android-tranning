package com.mservice.demo.rest;

import com.mservice.demo.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // seed=1&page=1&results=20
    @GET("?")
    Call<UserResponse> getUserData(@Query("seed") String seed, @Query("page") String page, @Query("results") String results);
}
