package com.example.foodmemo.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
  @SerializedName("result")
  private UserResult userResult = null;

  private static class UserResult {
    String token = null;
  }

  public String getRequestToken() {
    return userResult.token;
  }
}
