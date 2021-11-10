package com.naca.mealacle.p05;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.CartProduct;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.FoodDetailBinding;
import com.naca.mealacle.p06.BasketActivity;
import com.naca.mealacle.p07.OrderActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ProductActivity extends AppCompatActivity {

    private FoodDetailBinding binding;
    private boolean isOrder = false;
    private ImageView foodImage;
    private Bitmap foodBitmap;

    private ImageView detailImage;
    private Bitmap detailBitmap;

    private int selected_option = 0;
    private int count = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p05_activity_food);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar05.toolbar05;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("select");
        bind(food);

        binding.toolbar05.content.rating.setRating((float) 3.2);

        binding.toolbar05.content.purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.toolbar05.content.purchase.setVisibility(View.GONE);
                binding.toolbar05.content.order.setVisibility(View.VISIBLE);
                isOrder = true;
            }
        });

        RecyclerView option_recycler = binding.toolbar05.content.optionRecycler;
        option_recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        option_recycler.setHasFixedSize(true);
        List<HashMap<String, Object>> list = food.getOptions();

        OptionListAdapter mAdapter = new OptionListAdapter(list);

        mAdapter.setOnItemClickListener(new OptionListAdapter.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(View v, int position) {
                selected_option = position;
                mAdapter.setSelected_position(position);
                mAdapter.notifyDataSetChanged();
                binding.toolbar05.content.totalCost
                        .setText(Long.toString(count * (food.getPrice() +
                                Integer.parseInt(String.valueOf(food.getOptions().get(selected_option).get("price"))))) + "원");
            }
        });
        option_recycler.setAdapter(mAdapter);

        binding.toolbar05.content.count.setText(Integer.toString(count));
        binding.toolbar05.content.totalCost
                .setText(Long.toString(count * (food.getPrice() +
                        Integer.parseInt(String.valueOf(food.getOptions().get(selected_option).get("price"))))) + "원");

        binding.toolbar05.content.decrease.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(count != 1){
                    --count;
                    binding.toolbar05.content.count.setText(Integer.toString(count));
                    binding.toolbar05.content.totalCost
                            .setText(Long.toString(count * (food.getPrice() +
                                    Integer.parseInt(String.valueOf(food.getOptions().get(selected_option).get("price"))))) + "원");
                } else {
                    Toast toast = Toast.makeText(ProductActivity.this.getApplicationContext(),
                            "최소 1개 이상 구매하여야 합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        binding.toolbar05.content.increase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ++count;
                binding.toolbar05.content.count.setText(Integer.toString(count));
                binding.toolbar05.content.totalCost
                        .setText(Long.toString(count * (food.getPrice() +
                                Integer.parseInt(String.valueOf(food.getOptions().get(selected_option).get("price"))))) + "원");
            }
        });

        binding.toolbar05.content.gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartProduct cart = new CartProduct(food, selected_option, count);
                BasketActivity.list.add(cart);
                Toast toast = Toast.makeText(ProductActivity.this.getApplicationContext(),
                        "장바구니에 담겼습니다.", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

        binding.toolbar05.content.gotoPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, OrderActivity.class);
                CartProduct cart = new CartProduct(food, selected_option, count);
                intent.putExtra("activity", 5);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(isOrder){
            binding.toolbar05.content.purchase.setVisibility(View.VISIBLE);
            binding.toolbar05.content.order.setVisibility(View.GONE);
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
        binding.toolbar05.content.setVariable(BR.food_detail, food);

        foodImage = binding.toolbar05.content.foodimage;


        detailImage = binding.toolbar05.content.detailImage;

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL foodUrl = new URL(food.getImages());

                    HttpsURLConnection connect = (HttpsURLConnection) foodUrl.openConnection();
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream is = connect.getInputStream();
                    foodBitmap = BitmapFactory.decodeStream(is);

                    is.close();

                    URL detailUrl = new URL(food.getDescription());

                    connect = (HttpsURLConnection) detailUrl.openConnection();
                    connect.setDoInput(true);
                    connect.connect();

                    is = connect.getInputStream();
                    detailBitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();

        try {
            mThread.join();
            foodImage.setImageBitmap(foodBitmap);
            detailImage.setImageBitmap(detailBitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
