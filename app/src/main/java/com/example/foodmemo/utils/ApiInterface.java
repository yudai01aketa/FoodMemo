package com.example.foodmemo.utils;

import com.example.foodmemo.model.UserRequest;
import com.example.foodmemo.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
  @Headers("Content-Type:application/json")
  @POST("/sign_up")
  Call<UserResponse> signUp(@Body UserRequest userRequest);

  @Headers("Content-Type:application/json")
  @POST("/login")
  Call<UserResponse> login(@Body UserRequest userRequest);

  @Headers("Content-Type: application/json")
  @DELETE("/logout")
  Call<UserResponse> logout(@Header("Authorization") String token);
}
