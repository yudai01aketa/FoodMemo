package com.example.foodmemo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class HttpErrorUtils {
  private HttpErrorUtils() {}
  public static void errorHandlingActivity(String tag, String response, Context context, int message) {
    Log.w(tag, "Failure:" + response);
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }
  public static void errorHandlingFragment(String tag, String response, Activity activity, int message) {
    Log.w(tag, "Failure:" + response);
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
  }
}
