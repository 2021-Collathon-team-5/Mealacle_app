package com.naca.mealacle.p02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.User;
import com.naca.mealacle.databinding.UnivSelectBinding;
import com.naca.mealacle.p03.HomeActivity;
import com.naca.mealacle.p04.CategoryActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class UnivSelectActivity extends AppCompatActivity {

    private UnivSelectBinding binding;
    private View before_hovered = null;
    static LinkedList<String> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p02_activity_univselect);
        binding.setLifecycleOwner(this);

        binding.toolbar02.p2Content.search.setQueryHint("지역을 선택하세요");

        Toolbar toolbar = binding.toolbar02.toolbar02;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String[] locations = {"전체", "서울", "인천", "대전", "세종", "광주", "대구", "울산", "부산",
                "제주", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남"};

        RecyclerView location_recycler = binding.toolbar02.p2Content.locationRecycler;

        location_recycler.setLayoutManager(new GridLayoutManager(this, 4));
        location_recycler.setHasFixedSize(true);

        LocationAdapter mAdapter = new LocationAdapter(locations);
        location_recycler.setAdapter(mAdapter);

        RecyclerView univ_recycler = binding.toolbar02.p2Content.univRecycler;

        univ_recycler.setLayoutManager(new LinearLayoutManager(this));
        univ_recycler.setHasFixedSize(true);
        list.clear();

        UnivAdapter univAdapter = new UnivAdapter(list);
        univ_recycler.setAdapter(univAdapter);

        mAdapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                list.clear();
                if(before_hovered != null){
                    before_hovered.setHovered(false);
                }
                v.setHovered(true);
                before_hovered = v;
                if(position == 3){
                    binding.toolbar02.p2Content.search.setInputType(1);
                    list.add("충남대학교");
                    list.add("KAIST");
                }
                univ_recycler.setAdapter(new UnivAdapter(list));
            }
        });

        univAdapter.setOnItemClickListener(new UnivAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(UnivSelectActivity.this, UserInputActivity.class);
                User user = new User();
                user.setUniv(list.get(position));
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }
}