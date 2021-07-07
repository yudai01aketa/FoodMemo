package com.example.foodmemo.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodmemo.R;
import com.example.foodmemo.response.UserResponse;
import com.example.foodmemo.utils.ApiClient;
import com.example.foodmemo.utils.ApiInterface;
import com.example.foodmemo.utils.HttpErrorUtils;
import com.example.foodmemo.utils.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutFragment extends Fragment {
  private static final String TAG = LogoutFragment.class.getSimpleName();

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    requireActivity().setTitle(R.string.logout);

    View view = inflater.inflate(R.layout.fragment_logout, container, false);
    Button logoutButton = view.findViewById(R.id.logout_button);
    logoutButton.setOnClickListener((View v) -> {
      new AlertDialog.Builder(getContext())
              .setTitle(R.string.alert_title)
              .setMessage(R.string.alert_message)
              .setPositiveButton(R.string.alert_ok,
                      (dialog, which) -> logout())
              .setNegativeButton(R.string.alert_cancel,
                      (dialog, which) -> {
                      })
              .show();
    });
    return view;
  }

  private void logout() {
    ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
    String token = SharedPreferencesUtils.getToken(requireActivity());
    apiInterface.logout(token).enqueue(new Callback<UserResponse>() {
      @Override
      public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
        Toast.makeText(getActivity(), R.string.occurred_error, Toast.LENGTH_SHORT)
                .show();
      }

      @Override
      public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
        switch (response.code()) {
          case 200:
            Log.d(TAG, "Success:" + response.toString());
            SharedPreferencesUtils.removeToken(
                    requireActivity()
            );
            Toast.makeText(getActivity(), R.string.success_logout, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), com.example.foodmemo.activity.LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
            break;
          default:
            HttpErrorUtils.errorHandlingFragment(TAG, response.toString(), getActivity(), R.string.net_work_error);
        }
      }
    });
  }
}
