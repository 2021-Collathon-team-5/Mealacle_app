package com.naca.mealacle.p02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.databinding.UnivSelectBinding;
import com.naca.mealacle.p03.HomeActivity;

public class UnivSelectActivity extends AppCompatActivity {

    private UnivSelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p02_activity_univselect);
        binding.setLifecycleOwner(this);

        String[] locations = {"전체", "서울", "인천", "대전", "세종", "광주", "대구", "울산", "부산",
                "제주", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남"};

        RecyclerView location_recycler = binding.toolbar.p2Content.locationRecycler;

        location_recycler.setLayoutManager(new GridLayoutManager(this, 4));
        location_recycler.setHasFixedSize(true);

        LocationAdapter mAdapter = new LocationAdapter(locations);
        location_recycler.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplication(), HomeActivity.class);
                startActivity(intent);
                UnivSelectActivity.this.finish();
            }
        });
    }
}