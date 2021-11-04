package com.naca.mealacle.p06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.CartBinding;
import com.naca.mealacle.p07.OrderActivity;

import java.util.LinkedList;

public class BasketActivity extends AppCompatActivity {

    private CartBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p06_activity_cart);

        RecyclerView cart_recyclr = binding.toolbar.include.cartRecycler;
        cart_recyclr.setLayoutManager(new LinearLayoutManager(this));
        cart_recyclr.setHasFixedSize(true);

        LinkedList<Food> list = new LinkedList<>();
        for(int i = 1;i<=5;i++){
            list.add(new Food("test", i*10000));
        }

        CartListAdapter mAdapter = new CartListAdapter(list);
        cart_recyclr.setAdapter(mAdapter);

        View addMore = binding.toolbar.include.addmore;
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View gotoPurchase = binding.toolbar.include.gotoPurchase;
        gotoPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasketActivity.this, OrderActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            }
        });
    }
}
