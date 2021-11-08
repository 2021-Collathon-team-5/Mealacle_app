package com.naca.mealacle.p06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.CartBinding;
import com.naca.mealacle.p05.ProductActivity;
import com.naca.mealacle.p07.OrderActivity;

import java.util.LinkedList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {

    private CartBinding binding;
    public static LinkedList<Food> list = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p06_activity_cart);

        Toolbar toolbar = binding.toolbar06.toolbar06;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView cart_recyclr = binding.toolbar06.include.cartRecycler;
        cart_recyclr.setLayoutManager(new LinearLayoutManager(this));
        cart_recyclr.setHasFixedSize(true);

        CartListAdapter mAdapter = new CartListAdapter(list);
        mAdapter.setOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId()){
                    case R.id.increase:
                        Log.d("INCREASE", "increase");
                        break;
                    case R.id.decrease:
                        Log.d("DECREASE", "decrease");
                        break;
                }
            }
        });
        cart_recyclr.setItemAnimator(new DefaultItemAnimator());

        cart_recyclr.setAdapter(mAdapter);

        View gotoPurchase = binding.toolbar06.include.gotoPurchase;
        gotoPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size() == 0){
                    Toast toast = Toast.makeText(BasketActivity.this.getApplicationContext(),
                            "장바구니가 비어있습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(BasketActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
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
