package com.naca.mealacle.p05;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.FoodDetailBinding;
import com.naca.mealacle.p06.BasketActivity;
import com.naca.mealacle.p07.OrderActivity;

import java.util.LinkedList;

public class ProductActivity extends AppCompatActivity {

    private FoodDetailBinding binding;
    private boolean isOrder = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p05_activity_food);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar.toolbar;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("select");
        bind(food);

        binding.toolbar.content.purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.toolbar.content.purchase.setVisibility(View.GONE);
                binding.toolbar.content.order.setVisibility(View.VISIBLE);
                isOrder = true;
            }
        });

        binding.toolbar.content.gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(ProductActivity.this.getApplicationContext(),
                        "장바구니에 담겼습니다.", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

        binding.toolbar.content.gotoPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, OrderActivity.class);
                LinkedList<Food> list = new LinkedList<>();
                list.add(food);
                intent.putExtra("list", list);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(isOrder){
            binding.toolbar.content.purchase.setVisibility(View.VISIBLE);
            binding.toolbar.content.order.setVisibility(View.GONE);
            isOrder = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p05_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.cart:{
                Intent intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void bind(Food food){
        binding.toolbar.content.setVariable(BR.name_detail, food.getName());
        binding.toolbar.content.setVariable(BR.cost_detail, Integer.toString(food.getCost()) + "원");
    }
}
