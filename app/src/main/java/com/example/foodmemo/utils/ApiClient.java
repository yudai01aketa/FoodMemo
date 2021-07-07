package com.example.foodmemo.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
  static final String BASE_URL = "http://localhost:3000";

  private static final HttpLoggingInterceptor logging =
          new HttpLoggingInterceptor();

  public static HttpLoggingInterceptor getLogging() {
    logging.level(HttpLoggingInterceptor.Level.BODY);
    return logging;
  }

  private static final OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(getLogging())
          .build();

  private static final Gson gson = new GsonBuilder()
          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
          .create();

  public static Retrofit getRetrofitInstance() {
    return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
  }
}
