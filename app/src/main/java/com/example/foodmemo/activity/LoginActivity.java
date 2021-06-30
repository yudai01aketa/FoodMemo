package com.example.foodmemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foodmemo.R;

public class LoginActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signUpButton = findViewById(R.id.to_sign_up_button);
        signUpButton.setOnClickListener((View v) -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}
