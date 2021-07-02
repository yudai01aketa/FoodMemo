package com.example.foodmemo.utils;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.foodmemo.R;

import java.util.regex.Pattern;

public class ValidationUtils {
  public static Integer validateLogin(String email, String password) {
    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
      return R.string.is_empty;
    } else if (isInvalidEmail(email) || isInvalidPassword(password)) {
      return R.string.invalid_email_password;
    }
    return null;
  }

  public static Integer validateSignUp(String email, String password, String passwordConfirm) {
    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
      return R.string.is_empty;
    } else if (isInvalidEmail(email) || isInvalidPassword(password)) {
      return R.string.invalid_email_password;
    } else if (isNotMatchedPassword(password, passwordConfirm)) {
      return R.string.not_match_password;
    }
    return null;
  }

  public static Integer validateFood(String name, String price, String date) {
    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(date)) {
      return R.string.is_empty;
    }
    return null;
  }

  private static boolean isInvalidEmail(String email) {
    return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  private static boolean isInvalidPassword(String password) {
    return !Pattern.compile("^.{6,}").matcher(password).matches();
  }

  private static boolean isNotMatchedPassword(String password, String passwordConfirm) {
    return !password.equals(passwordConfirm);
  }
}
