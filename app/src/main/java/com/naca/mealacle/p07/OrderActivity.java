package com.naca.mealacle.p07;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.ReceiptBinding;
import com.naca.mealacle.p08.OrderSuccessActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private ReceiptBinding binding;
    private boolean info = false;
    private int info_height = 0;
    private boolean order = false;
    private int order_height = 0;
    private boolean purchase = false;
    private int purchase_height = 0;
    private boolean way = false;
    private int way_height = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p07_activity_receipt);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        List<Food> list = (List<Food>) intent.getSerializableExtra("list");

        RecyclerView order_recycler = binding.toolbar.include.orderRecycle;
        order_recycler.setLayoutManager(new LinearLayoutManager(this));
        order_recycler.setHasFixedSize(true);

        OrderAdapter mAdapter = new OrderAdapter(list);
        order_recycler.setAdapter(mAdapter);

        binding.toolbar.include.infoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar.include.info;
                if(info_height == 0)
                    info_height = layout.getHeight();
                viewVisibility(info, layout, info_height);
                info = !info;
            }
        });

        binding.toolbar.include.orderInfoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar.include.orderRecycle;
                if(order_height == 0)
                    order_height = layout.getHeight();
                viewVisibility(order, layout, order_height);
                order = !order;
            }
        });

        binding.toolbar.include.purchaseInfoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar.include.purchase;
                if(purchase_height == 0)
                    purchase_height = layout.getHeight();
                viewVisibility(purchase, layout, purchase_height);
                purchase = !purchase;
            }
        });

        binding.toolbar.include.wayInfoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar.include.way;
                if(way_height == 0)
                    way_height = layout.getHeight();
                viewVisibility(way, layout, way_height);
                way = !way;
            }
        });

        binding.toolbar.include.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, OrderSuccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void viewVisibility(boolean isExpanded, View view, int height){
        ValueAnimator va = isExpanded ?
                ValueAnimator.ofInt(1, height) :
                ValueAnimator.ofInt(height, 1);
        va.setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                view.getLayoutParams().height = value;
                view.requestLayout();
            }
        });
        va.start();
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
