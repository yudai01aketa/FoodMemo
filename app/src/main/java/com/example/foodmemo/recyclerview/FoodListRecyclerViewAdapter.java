package com.example.foodmemo.recyclerview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodmemo.recyclerview.FoodListRecyclerViewAdapter.FoodListViewHolder;
import com.example.foodmemo.R;
import com.example.foodmemo.model.FoodData;

import java.util.List;


public class FoodListRecyclerViewAdapter extends RecyclerView.Adapter<FoodListViewHolder> {
  private final List<FoodData> foodListData;
  private onItemClickListener listener;

  public interface onItemClickListener {
    void onClick(FoodData foodList);
  }
  public void setOnItemClickListener(onItemClickListener listener) {
    this.listener = listener;
  }

  public FoodListRecyclerViewAdapter(List<FoodData> foodListData) {
    this.foodListData = foodListData;
  }

  public static class FoodListViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameView = itemView.findViewById(R.id.text_view_food_name);
    private final TextView calorieView = itemView.findViewById(R.id.text_view_food_calorie);
    private final TextView dateView = itemView.findViewById(R.id.text_view_food_date);
    private final ImageView imageView = itemView.findViewById(R.id.image_view);

    public FoodListViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

  @NonNull
  @Override
  public FoodListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_food_list, parent, false);
    return new FoodListViewHolder(view);
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onBindViewHolder(FoodListViewHolder holder, int position) {
    final FoodData foodList = foodListData.get(position);
    holder.nameView.setText(foodList.name);

    if (foodList.calorie == null) {
      foodList.calorie = 0;
    }
    holder.calorieView.setText(foodList.calorie.toString());
    holder.dateView.setText(foodList.date);
    Glide.with(holder.itemView).load(foodList.image).into(holder.imageView);
    holder.itemView.setOnClickListener(view ->
            listener.onClick(foodList));
  }

  @Override
  public int getItemCount() {
    return foodListData.size();
  }
}
