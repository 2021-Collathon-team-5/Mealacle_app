package com.naca.mealacle.p15;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Delivery;
import com.naca.mealacle.databinding.DeliveryBinding;

import java.util.LinkedList;

public class DeliveryActivity extends AppCompatActivity {

    private DeliveryBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p15_activity_delivery);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar15.toolbar15;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinkedList<Delivery> list = new LinkedList<>();
        list.add(new Delivery("대전광역시 유성구 대학로 99(충남대학교) 기숙사 8동 401호",
                "월남쌈 쿠킹박스 밀키트 3개"));
        list.add(new Delivery("대전광역시 유성구 대학로 99(충남대학교) 기숙사 8동 401호",
                "월남쌈 쿠킹박스 밀키트 3개"));
        list.add(new Delivery("대전광역시 유성구 대학로 99(충남대학교) 기숙사 8동 401호",
                "월남쌈 쿠킹박스 밀키트 3개"));

        RecyclerView deliver_recycler = binding.toolbar15.include.deliveryRecycler;
        deliver_recycler.setLayoutManager(new LinearLayoutManager(this));
        deliver_recycler.setHasFixedSize(true);

        DeliveryAdapter mAdapter = new DeliveryAdapter(list);
        mAdapter.setOnItemClickListener(new DeliveryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId()){
                    case R.id.deliver_complete:
                        TextView textView = (TextView) v;
                        textView.setEnabled(false);
                        textView.setText("완료");
                        break;
                }
            }
        });
        deliver_recycler.setAdapter(mAdapter);



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
