package com.naca.mealacle.p12;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.data.Order;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.RiderBinding;
import com.naca.mealacle.p02.UserInputActivity;
import com.naca.mealacle.p13.RiderPageActivity;
import com.naca.mealacle.p14.EmploymentActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RiderActivity extends AppCompatActivity {

    private RiderBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LinkedList<String> foodIDList = new LinkedList<>();
    private LinkedList<String> idList = new LinkedList<>();
    private LinkedList<Order> orderList = new LinkedList<>();
    private LinkedList<Store> storeList = new LinkedList<>();
    private LinkedList<String> orderID = new LinkedList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p12_activity_rider);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar12.toolbar12;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.toolbar12.include.setVariable(BR.user_rider, UserInputActivity.user);

        db.collection("food").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                foodIDList.add(doc.getId());
                            }
                        }
                    }
                });

        binding.toolbar12.include.progress.setText("진행중인 배달 : " + UserInputActivity.user.getRiderID().size() + "개");

        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new FoodHandler(), 500);
        hd.postDelayed(new RiderHandler(), 1000);
        hd.postDelayed(new AdaptHandler(), 1500);
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

    private class FoodHandler implements Runnable{

        @Override
        public void run() {
            for(int i = 0;i<foodIDList.size();i++){
                int finalI = i;
                db.collection("order").whereEqualTo("foodID", foodIDList.get(i)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            idList.add(foodIDList.get(finalI));
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                orderList.add(doc.toObject(Order.class));
                                orderID.add(doc.getId());
                                Log.d("SIZE", Integer.toString(orderList.size()));
                            }
                        } else {
                            Log.e("error", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        }
    }

    private class RiderHandler implements Runnable {

        @Override
        public void run() {
            for(int i = 0;i<orderList.size();i++){
                Order order = orderList.get(i);
                Log.e("ready", Boolean.toString(!order.isReady()));
                Log.e("assign", Boolean.toString(order.isAssign()));
                Log.e("total", Boolean.toString(!order.isReady() || order.isAssign()));
                if(!order.isReady() || order.isAssign()){
                    Log.e("ready", Boolean.toString(order.isReady()));
                    continue;
                }
                Store store = new Store();
                final Food[] food = new Food[1];
                final boolean[] temp = {true};
                db.collection("food").document(order.getFoodID()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot doc = task.getResult();
                                    Map<String, Object> data = doc.getData();
                                    for(int j = 0;j<storeList.size();j++){
                                        if(String.valueOf(data.get("name")).equals(storeList.get(j).getName())){
                                            temp[0] = false;
                                            Log.d("TEST", "break");
                                            break;
                                        }
                                    }
                                    if(temp[0]){
                                        food[0] = new Food(doc.getId(),
                                                String.valueOf(data.get("category")),
                                                String.valueOf(data.get("description")),
                                                String.valueOf(((List<String>)data.get("image")).get(0)),
                                                String.valueOf(data.get("name")),
                                                (List<HashMap<String, Object>>) data.get("options"),
                                                String.valueOf(data.get("origin")),
                                                Long.parseLong(String.valueOf(data.get("price"))),
                                                (HashMap<String, Object>) data.get("seller"));
                                        store.setProduct(food[0].getName());
                                        store.setProductID(food[0].getProductID());
                                        store.setImage(food[0].getImages());
                                    }
                                }
                            }
                        });
                if(!temp[0])
                    continue;
                db.collection("seller").document(order.getSellerID()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot doc = task.getResult();
                                    Map<String, Object> data = doc.getData();
                                    store.setAddress(String.valueOf(data.get("address")));
                                    store.setName(String.valueOf(data.get("name")));
                                    store.setAdderName(String.valueOf(
                                            ((HashMap<String, Object>)((HashMap<String, Object>) data.get("profile"))
                                            .get(Integer.toString(order.getProfile_idx())))
                                            .get("profileName")));
                                }
                            }
                        });
                store.setOrderID(orderID.get(i));
                store.setCount(store.getCount() + order.getCount());

                storeList.add(store);
            }
            Log.e("TEST", "endStoring");
        }
    }

    private class AdaptHandler implements Runnable {

        @Override
        public void run() {
            Log.d("TEST", "startadapting");
            LinkedList<Store> list = new LinkedList<>();
            Boolean isAlready;
            for(int i = 0;i<storeList.size();i++){
                Store temp1 = storeList.get(i);
                isAlready = false;
                for(int j = 0;j<list.size();j++){
                    Store temp2 = list.get(j);
                    if(temp1.getProductID().equals(temp2.getProductID())){
                        temp2.getRiderList().add(temp1);
                        temp2.setCount(temp2.getCount() + temp1.getCount());
                        isAlready = true;
                    }
                }
                if(!isAlready){
                    list.add(temp1);
                }
            }
            RecyclerView store_recycle = binding.toolbar12.include.storeRecycle;
            store_recycle.setLayoutManager(new LinearLayoutManager(RiderActivity.this));
            store_recycle.setHasFixedSize(true);

            RiderAdapter mAdapter = new RiderAdapter(list);
            mAdapter.setOnItemClickListener(new RiderAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(RiderActivity.this, EmploymentActivity.class);
                    intent.putExtra("store", list.get(position));
                    startActivity(intent);
                }
            });
            store_recycle.setAdapter(mAdapter);

            binding.toolbar12.include.riderInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RiderActivity.this, RiderPageActivity.class);
                    startActivity(intent);
                }
            });
            Log.d("TEST", "Test");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestart() {
        orderList.clear();
        orderID.clear();
        storeList.clear();
        binding.toolbar12.include.progress.setText("진행중인 배달 : " + UserInputActivity.user.getRiderID().size() + "개");

        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new FoodHandler(), 500);
        hd.postDelayed(new RiderHandler(), 1000);
        hd.postDelayed(new AdaptHandler(), 2000);
        super.onRestart();
    }
}
