package com.naca.mealacle.p11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.OrderedBinding;
import com.naca.mealacle.p05.ProductActivity;

import java.util.LinkedList;

public class OrderedHistoryActivity extends AppCompatActivity {

    private OrderedBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p11_activity_ordered);
        binding.setLifecycleOwner(this);

        LinkedList<Food> list = new LinkedList<>();

        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<3;i++){
            sb.append("월남쌈 쿠킹박스 밀키트");
            list.add(new Food(sb.toString(), i*10000));
        }

        RecyclerView ordered_recycler = binding.toolbar.include.orderedRecycler;
        ordered_recycler.setLayoutManager(new LinearLayoutManager(this));
        ordered_recycler.setHasFixedSize(true);

        OrderedAdapter mAdapter = new OrderedAdapter(list);
        mAdapter.setOnItemClickListener(new OrderedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(OrderedHistoryActivity.this, ProductActivity.class);
                intent.putExtra("select", list.get(position));
                startActivity(intent);
            }
        });
        ordered_recycler.setAdapter(mAdapter);


    }
}
