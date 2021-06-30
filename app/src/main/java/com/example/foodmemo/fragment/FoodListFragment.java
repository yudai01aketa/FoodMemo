package com.example.foodmemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmemo.R;
import com.example.foodmemo.model.FoodData;

import java.util.ArrayList;
import java.util.List;


public class FoodListFragment extends Fragment {

  private final List<FoodData> dataSet = new ArrayList<>();
  private final com.example.foodmemo.recyclerview.FoodListRecyclerViewAdapter
          foodListAdapter = new com.example.foodmemo.recyclerview.FoodListRecyclerViewAdapter(dataSet);

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    requireActivity().setTitle(R.string.food_list);
    View view = inflater.inflate(R.layout.fragment_food_list, container, false);

    RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(foodListAdapter);

    // リストごとに境界線をつける
    RecyclerView.ItemDecoration itemDecoration;
    itemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
    recyclerView.addItemDecoration(itemDecoration);

    setHasOptionsMenu(true);
    return view;
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.add, menu);
  }
}
