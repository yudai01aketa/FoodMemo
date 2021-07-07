package com.example.foodmemo.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodmemo.R;
import com.example.foodmemo.model.UserRequest;
import com.example.foodmemo.response.UserResponse;
import com.example.foodmemo.utils.ApiClient;
import com.example.foodmemo.utils.ApiInterface;
import com.example.foodmemo.utils.HttpErrorUtils;
import com.example.foodmemo.utils.KeyboardUtils;
import com.example.foodmemo.utils.SharedPreferencesUtils;
import com.example.foodmemo.utils.ValidationUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.login);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener((View v) -> {
            EditText emailText = findViewById(R.id.edit_text_email);
            EditText passwordText = findViewById(R.id.edit_text_password);
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            Integer errorMessage = ValidationUtils.validateLogin(email, password);
            if (errorMessage == null) {
                login(email, password);
            } else {
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        Button signUpButton = findViewById(R.id.to_sign_up_button);
        signUpButton.setOnClickListener((View v) -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View focusView = this.getCurrentFocus();
        if (focusView != null) {
            KeyboardUtils.hideKeyboard(focusView);
        }
        return false;
    }

    private void login(String email, String password) {
        ApiInterface retrofitInstance = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        UserRequest requestBody = new UserRequest(email, password);
        retrofitInstance.login(requestBody).enqueue(new Callback<UserResponse>() {
            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.occurred_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                switch (response.code()) {
                    case 200:
                        Log.d(TAG, "Success:" + response.toString());
                        SharedPreferencesUtils.putToken(
                                LoginActivity.this, Objects.requireNonNull(response.body()).getRequestToken()
                        );
                        Toast.makeText(getApplication(), R.string.success_login, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), FoodListActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 401:
                        HttpErrorUtils.errorHandlingActivity(TAG, response.toString(), LoginActivity.this, R.string.unauthorized_error);
                        break;
                    case 500:
                        HttpErrorUtils.errorHandlingActivity(TAG, response.toString(), LoginActivity.this, R.string.server_error);
                        break;
                    default:
                        HttpErrorUtils.errorHandlingActivity(TAG, response.toString(), LoginActivity.this, R.string.net_work_error);
                }
            }
        });
    }
}
