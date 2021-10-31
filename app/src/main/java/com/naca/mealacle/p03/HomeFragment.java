package com.naca.mealacle.p03;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.HomeToolbarBinding;
import com.naca.mealacle.p04.CategoryActivity;
import com.naca.mealacle.p05.ProductActivity;

import java.util.LinkedList;

public class HomeFragment extends Fragment {

    private HomeToolbarBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.p03_app_toolbar_home, container, false);

        View view = binding.getRoot();

        String[] category = {"한식", "중식", "카페 / 브런치", "일식", "아시안 / 양식",
                "분식", "양식", "탕 / 찌개", "야식"};

        RecyclerView category_recycler = binding.homeFragment.category;
        category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 5));
        category_recycler.setHasFixedSize(true);

        CategoryAdapter categoryAdapter = new CategoryAdapter(category);
        category_recycler.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        LinkedList<Food> recentList = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        for(int i = 0;i<5;i++){
            sb.append("test");
            recentList.add(new Food(sb.toString(), i*1000));
        }

        RecyclerView recent_recycler = binding.homeFragment.recent;
        recent_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recent_recycler.setHasFixedSize(true);

        FoodAdapter recentAdapter = new FoodAdapter(recentList);
        recent_recycler.setAdapter(recentAdapter);

        recentAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), ProductActivity.class);
                intent.putExtra("select", recentList.get(position));
                startActivity(intent);
            }
        });

        LinkedList<Food> orderedList = new LinkedList<>();
        sb = new StringBuilder();

        for(int i = 0;i<7;i++){
            sb.append("test");
            sb.append("test");
            orderedList.add(new Food(sb.toString(), i*1000));
        }

        RecyclerView ordered_recycler = binding.homeFragment.ordered;
        ordered_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ordered_recycler.setHasFixedSize(true);

        FoodAdapter orderedAdapter = new FoodAdapter(orderedList);
        ordered_recycler.setAdapter(orderedAdapter);

        orderedAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), ProductActivity.class);
                intent.putExtra("select", orderedList.get(position));
                startActivity(intent);
            }
        });

        return view;


    }
}
