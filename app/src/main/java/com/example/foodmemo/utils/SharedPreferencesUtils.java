package com.example.foodmemo.utils;

import android.content.Context;

import androidx.annotation.NonNull;

public class SharedPreferencesUtils {
  private static final String USER_DATA = "userData";
  private static final String TOKEN = "token";

  public static String getToken(@NonNull Context context) {
    return context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(TOKEN, null);
  }

  public static void putToken(@NonNull Context context, @NonNull String token) {
    context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .edit()
            .putString(TOKEN, token)
            .apply();
  }

  public static void removeToken(@NonNull Context context) {
    context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .edit()
            .remove(TOKEN)
            .apply();
  }
}
