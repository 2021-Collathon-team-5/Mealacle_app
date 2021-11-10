package com.naca.mealacle.p03;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeToolbarBinding binding;
    private String univ;
    private int currentPage = 0;

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

        List<Integer> list = new LinkedList<>();
        list.add(R.drawable.banner_1);
        list.add(R.drawable.banner_2);
        list.add(R.drawable.banner_3);
        list.add(R.drawable.banner_4);
        list.add(R.drawable.banner_5);
        list.add(R.drawable.banner_6);
        list.add(R.drawable.banner_7);
        HomeEventAdapter eAdapter = new HomeEventAdapter(getContext(), list);

        ViewPager banner = binding.homeFragment.viewPager;

        Timer timer = new Timer();
        banner.setAdapter(eAdapter);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == eAdapter.getCount()){
                    currentPage = 0;
                }
                banner.setCurrentItem(currentPage++, true);
            }
        };

        banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = banner.getCurrentItem();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0, 5000);


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

        recentList.add(new Food("일품 소고기 고추잡채 꽃빵 (2인)", 11900, R.drawable.food_0));
        recentList.add(new Food("감바스알아히요 (2인)", 17900, R.drawable.food_1));
        recentList.add(new Food("밀푀유나베 (2인)", 16900, R.drawable.food_2));
        recentList.add(new Food("찹스테이크 (2인)", 15900, R.drawable.food_3));
        recentList.add(new Food("양장피 (2인)", 15900, R.drawable.food_4));

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

        orderedList.add(new Food("월남쌈 (2인)", 15900, R.drawable.food_5));
        orderedList.add(new Food("인생 닭갈비 (2인)", 17900, R.drawable.food_6));
        orderedList.add(new Food("베이컨야채말이 (2인)", 12900, R.drawable.food_7));
        orderedList.add(new Food("르네 프렌치스테이크 (2인)", 14900, R.drawable.food_8));
        orderedList.add(new Food("고기듬뿍 토마토 파스타 (2인)", 13900, R.drawable.food_9));

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
