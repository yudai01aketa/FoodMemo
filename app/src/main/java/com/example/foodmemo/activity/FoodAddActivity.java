package com.example.foodmemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodmemo.R;
import com.example.foodmemo.utils.KeyboardUtils;

import java.io.BufferedInputStream;
import java.util.Calendar;
import java.util.Objects;

public class FoodAddActivity extends AppCompatActivity {

  @SuppressLint("DefaultLocale")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.add_food);
    setContentView(R.layout.activity_food_add);

    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    Button addImageButton = findViewById(R.id.add_image_button);
    addImageButton.setOnClickListener((View v) -> {
      Intent intent = new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      launcher.launch(intent);
    });

    EditText showDate = findViewById(R.id.edit_text_food_date);
    showDate.setOnClickListener(v -> {
      final Calendar date = Calendar.getInstance();
      @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog;
      datePickerDialog = new DatePickerDialog(
              FoodAddActivity.this,
              (view, year, month, dayOfMonth) ->
                      showDate.setText(String.format("%d/%01d/%01d", year, month + 1, dayOfMonth)),
              date.get(Calendar.YEAR),
              date.get(Calendar.MONTH),
              date.get(Calendar.DATE)
      );
      datePickerDialog.show();
    });
  }

  ActivityResultLauncher<Intent> launcher = registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              try {
                if (data == null) {
                  return;
                }
                BufferedInputStream inputStream = new BufferedInputStream(
                        getContentResolver().openInputStream(data.getData()));
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                ImageView imageViewFood = findViewById(R.id.image_view_food);
                imageViewFood.setImageBitmap(image);
              } catch (Exception ignored) {
                Toast.makeText(this, R.string.occurred_error, Toast.LENGTH_LONG).show();
              }
            }
          });

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.save, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.save:
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
}
