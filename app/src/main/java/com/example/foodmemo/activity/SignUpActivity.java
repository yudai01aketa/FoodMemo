package com.example.foodmemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.create_account);
        setContentView(R.layout.activity_sign_up);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                EditText emailText = findViewById(R.id.edit_text_email);
                EditText passwordText = findViewById(R.id.edit_text_password);
                EditText passwordConfirmText = findViewById(R.id.edit_text_password_confirm);

                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String passwordConfirm = passwordConfirmText.getText().toString();

                Integer errorMessage = ValidationUtils.validateSignUp(email, password, passwordConfirm);
                if (errorMessage == null) {
                    signUp(email, password);
                } else {
                    Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final View focusView = this.getCurrentFocus();
        if (focusView != null) {
            KeyboardUtils.hideKeyboard(focusView);
        }
        return false;
    }

    private void signUp(String email, String password) {
        ApiInterface retrofitInstance = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        UserRequest requestBody = new UserRequest(email, password);
        retrofitInstance.signUp(requestBody).enqueue(new Callback<UserResponse>() {
            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(SignUpActivity.this, R.string.occurred_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                switch (response.code()) {
                    case 200:
                        Log.d(TAG, "Success:" + response.toString());
                        SharedPreferencesUtils.putToken(
                                SignUpActivity.this, Objects.requireNonNull(response.body()).getRequestToken()
                        );
                        Toast.makeText(getApplication(), R.string.success_sign_up, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), FoodListActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 401:
                        HttpErrorUtils.errorHandlingActivity(TAG, response.toString(), SignUpActivity.this, R.string.unauthorized_error);
                        break;
                    case 500:
                        HttpErrorUtils.errorHandlingActivity(TAG, response.toString(), SignUpActivity.this, R.string.server_error);
                        break;
                    default:
                        HttpErrorUtils.errorHandlingActivity(TAG, response.toString(), SignUpActivity.this, R.string.net_work_error);
                }
            }
        });
    }
}
