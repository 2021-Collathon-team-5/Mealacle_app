package com.naca.mealacle.p07;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import com.naca.mealacle.p06.BasketActivity;
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

        Toolbar toolbar = binding.toolbar07.toolbar07;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        RecyclerView order_recycler = binding.toolbar07.include.orderRecycle;
        order_recycler.setLayoutManager(new LinearLayoutManager(this));
        order_recycler.setHasFixedSize(true);

        OrderAdapter mAdapter = new OrderAdapter(BasketActivity.list);
        order_recycler.setAdapter(mAdapter);

        binding.toolbar07.include.infoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar07.include.info;
                if(info_height == 0)
                    info_height = layout.getHeight();
                viewVisibility(info, layout, info_height);
                if(info){
                    binding.toolbar07.include.infoArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                } else {
                    binding.toolbar07.include.infoArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                info = !info;
            }
        });

        binding.toolbar07.include.orderInfoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar07.include.orderRecycle;
                if(order_height == 0)
                    order_height = layout.getHeight();
                viewVisibility(order, layout, order_height);
                if(order){
                    binding.toolbar07.include.orderArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                } else {
                    binding.toolbar07.include.orderArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                order = !order;
            }
        });

        binding.toolbar07.include.purchaseInfoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar07.include.purchase;
                if(purchase_height == 0)
                    purchase_height = layout.getHeight();
                viewVisibility(purchase, layout, purchase_height);
                if(purchase){
                    binding.toolbar07.include.purchaseArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                } else {
                    binding.toolbar07.include.purchaseArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                purchase = !purchase;
            }
        });

        binding.toolbar07.include.wayInfoFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = binding.toolbar07.include.way;
                if(way_height == 0)
                    way_height = layout.getHeight();
                viewVisibility(way, layout, way_height);
                if(way){
                    binding.toolbar07.include.wayArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                } else {
                    binding.toolbar07.include.wayArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                way = !way;
            }
        });

        CheckBox checkBox = binding.toolbar07.include.necessary;
        TextView complete = binding.toolbar07.include.complete;

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    complete.setEnabled(true);
                    complete.setText("@{}");
                } else {
                    complete.setEnabled(false);
                    complete.setText("개인정보 수집 항목을 확인해주세요.");
                }
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, OrderSuccessActivity.class);
                startActivity(intent);
                BasketActivity.list.clear();
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
