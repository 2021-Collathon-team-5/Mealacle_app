package com.naca.mealacle.p4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.CategoryFragmentBinding;
import com.naca.mealacle.p5.ProductActivity;

import java.util.LinkedList;

public class CategoryFragment extends Fragment {

    private CategoryFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.p04_fragment_category, container, false);

        View view = binding.getRoot();

        LinkedList<Food> list = new LinkedList<>();

        Bundle b = getArguments();
        int pos = b.getInt("pos");
        for(int i = 1;i<=pos;i++){
            list.add(new Food("test", i*10000));
        }

        RecyclerView food_recycler = binding.foodlist;
        food_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        food_recycler.setHasFixedSize(true);

        FoodListAdapter foodAdapter = new FoodListAdapter(list);
        foodAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), ProductActivity.class);
                intent.putExtra("select", list.get(position));
                startActivity(intent);
            }
        });
        food_recycler.setAdapter(foodAdapter);

        String[] sort = {"기본순", "별점순", "가격 높은 순", "가격 낮은 순"};


        RecyclerView sort_recycler= binding.sort;
        sort_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        sort_recycler.setHasFixedSize(true);

        SortListAdapter sortAdapter = new SortListAdapter(sort);
        sort_recycler.setAdapter(sortAdapter);

        return view;
    }
}
