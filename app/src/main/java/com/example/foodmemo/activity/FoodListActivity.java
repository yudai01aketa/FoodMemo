package com.example.foodmemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodmemo.R;
import com.example.foodmemo.fragment.FoodListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FoodListActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_activity_food_list);

    com.example.foodmemo_android_java.utils.FragmentUtils.switchFragment(
            getSupportFragmentManager(), new FoodListFragment()
    );

    BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
    navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
  }

  private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
          = item -> {
    switch (item.getItemId()) {
      case R.id.navigation_food:
        com.example.foodmemo_android_java.utils.FragmentUtils.switchFragment(getSupportFragmentManager(), new FoodListFragment());
        return true;
      case R.id.navigation_setting:
        com.example.foodmemo_android_java.utils.FragmentUtils.switchFragment(getSupportFragmentManager(), new com.example.foodmanager_android_java.fragment.LogoutFragment());
        return true;
      default:
        return false;
    }
  };
}
