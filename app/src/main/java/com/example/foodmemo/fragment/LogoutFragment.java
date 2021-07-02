package com.example.foodmemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodmemo.R;

public class LogoutFragment extends Fragment {

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    requireActivity().setTitle(R.string.logout);

    View view = inflater.inflate(R.layout.fragment_logout, container, false);
    return view;
  }
}
