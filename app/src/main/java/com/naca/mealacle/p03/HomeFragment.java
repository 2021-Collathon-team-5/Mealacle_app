package com.naca.mealacle.p03;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Category;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.HomeToolbarBinding;
import com.naca.mealacle.p02.UnivSelectActivity;
import com.naca.mealacle.p02.UserInputActivity;
import com.naca.mealacle.p04.CategoryActivity;
import com.naca.mealacle.p05.ProductActivity;
import com.naca.mealacle.p09.NotifyActivity;

import java.util.LinkedList;

public class HomeFragment extends Fragment {

    private HomeToolbarBinding binding;
    private String univ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.p03_app_toolbar_home, container, false);

        univ = UserInputActivity.user.getUniv();

        Toolbar toolbar = binding.toolbar03;

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_alert:
                        Intent intent = new Intent(getContext(), NotifyActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        binding.toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UnivSelectActivity.class);
                startActivity(intent);
            }
        });

        binding.setVariable(BR.title, univ);

        View view = binding.getRoot();

        Category[] category = {new Category("한식", R.drawable.korean),
                new Category("중식", R.drawable.chinise),
                new Category("카페 / 브런치", R.drawable.cafe),
                new Category("일식", R.drawable.japanese),
                new Category("아시안", R.drawable.asian),
                new Category("분식", R.drawable.snack),
                new Category("양식", R.drawable.western),
                new Category("탕 / 찌개", R.drawable.stew),
                new Category("야식", R.drawable.midnight)};

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
                intent.putExtra("univ", univ);
                startActivity(intent);
            }
        });

        LinkedList<Food> recentList = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        for(int i = 0;i<5;i++){
            sb.append("test");
            recentList.add(new Food(sb.toString(), i*1000, R.drawable.ic_launcher_background));
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
            orderedList.add(new Food(sb.toString(), i*1000, R.drawable.ic_launcher_background));
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

        ContentValues contentValues = new ContentValues();
        contentValues.put("test", (new Food()).toString());

        return view;
    }
}
