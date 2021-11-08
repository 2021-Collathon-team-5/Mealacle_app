package com.naca.mealacle.p12;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.RiderBinding;
import com.naca.mealacle.p13.RiderPageActivity;
import com.naca.mealacle.p14.EmploymentActivity;

import java.util.LinkedList;

public class RiderActivity extends AppCompatActivity {

    private RiderBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p12_activity_rider);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar12.toolbar12;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinkedList<Store> list = new LinkedList<>();
        list.add(new Store("방콕식장", "대전광역시 유성구 봉명동 566-8 (봉명동)",
                "월남쌈 쿠킹박스 밀키트", "2000원 (개당)", "오후 9:00", R.drawable.ic_launcher_background));

        list.add(new Store("카페713", "대전광역시 유성구 죽동 713-7 1층",
                "티라미수 조각 케이크 세트 10개", "2000원 (개당)", "오후 9:00", R.drawable.ic_launcher_background));

        RecyclerView store_recycle = binding.toolbar12.include.storeRecycle;
        store_recycle.setLayoutManager(new LinearLayoutManager(this));
        store_recycle.setHasFixedSize(true);

        RiderAdapter mAdapter = new RiderAdapter(list);
        mAdapter.setOnItemClickListener(new RiderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(RiderActivity.this, EmploymentActivity.class);
                intent.putExtra("store", list.get(position));
                startActivity(intent);
            }
        });
        store_recycle.setAdapter(mAdapter);

        binding.toolbar12.include.riderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiderActivity.this, RiderPageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
