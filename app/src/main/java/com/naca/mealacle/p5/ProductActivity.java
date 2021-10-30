package com.naca.mealacle.p5;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.FoodDetailBinding;

public class ProductActivity extends AppCompatActivity {

    private FoodDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p05_activity_food);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("select");
        bind(food);

    }

    public void bind(Food food){
        binding.toolbar.content.setVariable(BR.name_detail, food.getName());
        binding.toolbar.content.setVariable(BR.cost_detail, Integer.toString(food.getCost()) + "원");
    }
}
