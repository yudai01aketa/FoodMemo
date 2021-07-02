package com.example.foodmemo.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.foodmemo.R;
import com.example.foodmemo.model.FoodData;
import com.example.foodmemo.utils.BitmapConvertUtils;
import com.example.foodmemo.utils.ValidationUtils;

import java.io.BufferedInputStream;
import java.util.Calendar;
import java.util.Objects;

public class FoodEditFragment extends Fragment {

  private static final String TAG = FoodEditFragment.class.getSimpleName();
  public static final String REQUEST_KEY = "FoodListFragment";
  private static final String ARGUMENT_FOOD = "foodData";
  private Integer foodId = 0;

  @SuppressLint("SetTextI18n")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    requireActivity().setTitle(R.string.edit_food);
    setupBackButton();
    setHasOptionsMenu(true);

    View view = inflater.inflate(R.layout.fragment_food_edit, container, false);
    Button addImageButton = view.findViewById(R.id.add_image_button);
    addImageButton.setOnClickListener((View v) -> {
      Intent intent = new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      launcher.launch(intent);
    });

    EditText foodName = view.findViewById(R.id.edit_text_food_name);
    EditText foodPrice = view.findViewById(R.id.edit_text_food_price);
    EditText foodDate = view.findViewById(R.id.edit_text_food_date);
    ImageView foodImage = view.findViewById(R.id.image_view_food);

    foodDate.setOnClickListener(v -> {
      final Calendar date = Calendar.getInstance();
      @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(
              requireActivity(),
              (View, year, month, dayOfMonth) ->
                      foodDate.setText(String.format("%d/%01d/%01d", year, month + 1, dayOfMonth)),
              date.get(Calendar.YEAR),
              date.get(Calendar.MONTH),
              date.get(Calendar.DATE)
      );
      datePickerDialog.show();
    });

    getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, (key, bundle) -> {
      final FoodData foodData = (FoodData) bundle.getSerializable(ARGUMENT_FOOD);
      foodId = foodData.id;
      foodName.setText(foodData.name);
      foodPrice.setText(foodData.calorie.toString());
      foodDate.setText(foodData.date);
      Glide.with(view.getContext()).load(foodData.image).into(foodImage);
    });

    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
      @Override
      public void handleOnBackPressed() {
        getParentFragmentManager().popBackStack();
      }
    };
    requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    return view;
  }

  ActivityResultLauncher<Intent> launcher = registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              try {
                BufferedInputStream inputStream = new BufferedInputStream(
                        requireActivity().getContentResolver().openInputStream(Objects.requireNonNull(data).getData()));
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                ImageView imageViewFood = requireView().findViewById(R.id.image_view_food);
                imageViewFood.setImageBitmap(image);
              } catch (Exception ignored) {
                Toast.makeText(requireActivity(), R.string.occurred_error, Toast.LENGTH_LONG).show();
              }
            }
          });

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.save, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.save:
        EditText nameText = requireActivity().findViewById(R.id.edit_text_food_name);
        EditText priceText = requireActivity().findViewById(R.id.edit_text_food_price);
        EditText dateText = requireActivity().findViewById(R.id.edit_text_food_date);

        String name = nameText.getText().toString();
        String price = priceText.getText().toString();
        String date = dateText.getText().toString();

        ImageView imageView = requireActivity().findViewById(R.id.image_view_food);
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        String image = BitmapConvertUtils.getBase64(bitmap);

        Integer errorMessage = ValidationUtils.validateFood(name, price, date);
        if (errorMessage == null) {
        } else {
          Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        }
        return true;
      case android.R.id.home:
        getParentFragmentManager().popBackStack();
        return true;
      default:
        return false;
    }
  }

  private void setupBackButton() {
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    ActionBar actionBar = Objects.requireNonNull(activity).getSupportActionBar();
    Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
  }
}
