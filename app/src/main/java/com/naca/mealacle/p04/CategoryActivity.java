package com.naca.mealacle.p04;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.CategoryBinding;
import com.naca.mealacle.p05.ProductActivity;
import com.naca.mealacle.p06.BasketActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {

    private CategoryBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LinkedList<Food> list = new LinkedList<>();
    private int selected_option = 0;
    private int pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p04_activity_category);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pos = bundle.getInt("position");
        String univ = bundle.getString("univ");

        Toolbar toolbar = binding.toolbar04.toolbar04;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar04.setVariable(BR.univ, univ);

        Bundle b = new Bundle(1);
        b.putInt("pos", pos);

        TabLayout tabs = binding.toolbar04.content.tabs;
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Objects.requireNonNull(tabs.getTabAt(pos)).select();
                        if (pos == 0){
                            updateData(selected_option);
                        }
                    }
                }, 100);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pos = tab.getPosition();
                updateData(selected_option);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        String[] sort = {"기본순", "별점순", "가격 높은 순", "가격 낮은 순"};

        RecyclerView sort_recycler= binding.toolbar04.content.sort;
        sort_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sort_recycler.setHasFixedSize(true);

        SortListAdapter sortAdapter = new SortListAdapter(sort);
        selected_option = sortAdapter.getSelected_position();
        sortAdapter.setOnItemClickListener(new SortListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                sortAdapter.setSelected_position(position);
                sortAdapter.notifyDataSetChanged();
                selected_option = position;
                updateData(selected_option);
            }
        });
        sort_recycler.setAdapter(sortAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p05_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.cart: {
                Intent intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateData(int position){
        CollectionReference docFood = db.collection("food");
        docFood.whereEqualTo("category", Integer.toString(pos)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    list.clear();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> data = document.getData();
                        list.add(new Food(document.getId(),
                                String.valueOf(data.get("category")),
                                String.valueOf(data.get("description")),
                                String.valueOf(((List<String>)data.get("image")).get(0)),
                                String.valueOf(data.get("name")),
                                (List<HashMap<String, Object>>) data.get("options"),
                                String.valueOf(data.get("origin")),
                                Long.parseLong(String.valueOf(data.get("price"))),
                                (HashMap<String, Object>) data.get("seller")));
                    }

                } else {
                    Log.d("FAIL", "ERROR", task.getException());
                }

                Comparator comparator = null;
                switch (position){
                    case 1:
                        comparator = new Comparator<Food>() {
                            @Override
                            public int compare(Food o1, Food o2) {
                                if(o1.getRating() < o2.getRating()) return 1;
                                if(o1.getRating() > o2.getRating()) return -1;
                                return 0;
                            }
                        };
                        Collections.sort(list, comparator);
                        break;
                    case 2:
                        comparator = new Comparator<Food>() {
                            @Override
                            public int compare(Food o1, Food o2) {
                                if(o1.getPrice() < o2.getPrice()) return 1;
                                if(o1.getPrice() > o2.getPrice()) return -1;
                                return 0;
                            }
                        };
                        Collections.sort(list, comparator);
                        break;
                    case 3:
                        comparator = new Comparator<Food>() {
                            @Override
                            public int compare(Food o1, Food o2) {
                                if(o1.getPrice() > o2.getPrice()) return 1;
                                if(o1.getPrice() < o2.getPrice()) return -1;
                                return 0;
                            }
                        };
                        Collections.sort(list, comparator);
                        break;
                }


                RecyclerView food_recycler = binding.toolbar04.content.foodlist;
                food_recycler.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
                food_recycler.setHasFixedSize(true);

                Log.e("LIST_SIZE", Integer.toString(list.size()));
                FoodListAdapter foodAdapter = new FoodListAdapter(list, CategoryActivity.this);
                foodAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(CategoryActivity.this, ProductActivity.class);
                        intent.putExtra("select", list.get(position));
                        startActivity(intent);
                    }
                });
                food_recycler.setAdapter(foodAdapter);
            }
        });
    }
}
