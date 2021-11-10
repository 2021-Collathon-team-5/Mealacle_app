package com.naca.mealacle.p11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.databinding.OrderedBinding;
import com.naca.mealacle.p01.SplashActivity;
import com.naca.mealacle.p05.ProductActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderedHistoryActivity extends AppCompatActivity {

    private OrderedBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinkedList<Food> list;
    List<String> idList;
    List<String> foodIdList;


    RecyclerView ordered_recycler;
    OrderedAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p11_activity_ordered);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar11.toolbar11;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new LinkedList<>();
        foodIdList = new LinkedList<>();

        StringBuilder sb = new StringBuilder();

        DocumentReference docRef = db.collection("user").document(SplashActivity.userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    idList = (List<String>) doc.getData().get("orderList");
                }
            }
        });
        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new Handler_1(), 300);
        hd.postDelayed(new Handler_2(), 600);
        hd.postDelayed(new HistoryHandler(), 1000);
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

    private class Handler_1 implements Runnable {

        @Override
        public void run() {
            CollectionReference colRef = db.collection("order");
            for(int i = 0;i<idList.size();i++){
                Log.d("ID", idList.get(i));
                colRef.document(idList.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            Map<String, Object> data = doc.getData();
                            foodIdList.add(String.valueOf(data.get("foodID")));

                        }
                    }
                });
            }
        }
    }

    private class Handler_2 implements Runnable {

        @Override
        public void run() {
            for(int i = 0;i<foodIdList.size();i++){
                Log.d("ID", foodIdList.get(i));
                db.collection("food")
                        .document(foodIdList.get(i))
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            Map<String, Object> data = doc.getData();
                            list.add(new Food(doc.getId(),
                                    String.valueOf(data.get("category")),
                                    String.valueOf(data.get("description")),
                                    String.valueOf(((List<String>)data.get("image")).get(0)),
                                    String.valueOf(data.get("name")),
                                    (List<HashMap<String, Object>>) data.get("options"),
                                    String.valueOf(data.get("origin")),
                                    Long.parseLong(String.valueOf(data.get("price"))),
                                    (HashMap<String, Object>) data.get("seller")));
                        }
                    }
                });
            }
        }
    }

    private class HistoryHandler implements Runnable {

        @Override
        public void run() {
            ordered_recycler = binding.toolbar11.include.orderedRecycler;
            ordered_recycler.setLayoutManager(new LinearLayoutManager(OrderedHistoryActivity.this));
            ordered_recycler.setHasFixedSize(true);

            mAdapter = new OrderedAdapter(list);
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
}
