package com.example.foodmemo_android_java.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.foodmemo.R;

public class FragmentUtils {
  public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
    fragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit();
  }

  public static void switchFragment(FragmentManager fragmentManager, Fragment fragment) {
    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    fragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit();
  }
}
