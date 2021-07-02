package com.example.foodmemo.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

public class KeyboardUtils {
  public static void hideKeyboard(@NonNull View focusView) {
    InputMethodManager inputMethodManager =
            (InputMethodManager) focusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(
            focusView.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
  }
}
