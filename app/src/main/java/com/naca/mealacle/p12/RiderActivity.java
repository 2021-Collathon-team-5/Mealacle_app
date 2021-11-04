package com.naca.mealacle.p12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

        LinkedList<Store> list = new LinkedList<>();
        list.add(new Store("방콕식장", "대전광역시 유성구 봉명동 566-8 (봉명동)",
                "월남쌈 쿠킹박스 밀키트", "2000원 (개당)", "오후 9:00"));

        list.add(new Store("카페713", "대전광역시 유성구 죽동 713-7 1층",
                "티라미수 조각 케이크 세트 10개", "2000원 (개당)", "오후 9:00"));

        RecyclerView store_recycle = binding.toolbar.include.storeRecycle;
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

        binding.toolbar.include.riderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiderActivity.this, RiderPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
