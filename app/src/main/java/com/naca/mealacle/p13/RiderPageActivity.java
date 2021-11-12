package com.naca.mealacle.p13;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Food;
import com.naca.mealacle.data.Order;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.RiderInfoBinding;
import com.naca.mealacle.p01.SplashActivity;
import com.naca.mealacle.p15.DeliveryActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RiderPageActivity extends AppCompatActivity {

    private RiderInfoBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Object> riderid = new LinkedList<>();
    private List<String> orderID = new LinkedList<>();
    private List<Order> orders = new LinkedList<>();
    private List<Store> stores = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p13_activity_riderinfo);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar13.toolbar13;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollectionReference userRef = db.collection("user");
        userRef.document(SplashActivity.userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                riderid = (List<Object>) documentSnapshot.get("riderID");
            }
        });

        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new RiderHandler(), 500);
        hd.postDelayed(new OrderHandler(), 1000);
        hd.postDelayed(new StoreHandler(), 1500);
        hd.postDelayed(new AdaptHandler(), 2000);
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

    private class RiderHandler implements Runnable{

        @Override
        public void run() {
            for(Object s : riderid){
                db.collection("rider").document(String.valueOf(s)).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<Object> objects = (List<Object>) documentSnapshot.get("orderid");
                                for(int i = 0;i<objects.size();i++){
                                    orderID.add(String.valueOf(objects.get(i)));
                                }
                            }
                        });
            }
        }
    }

    private class OrderHandler implements Runnable{

        @Override
        public void run() {
            for(String s : orderID){
                db.collection("order").document(s)
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        orders.add(documentSnapshot.toObject(Order.class));
                    }
                });
            }
        }
    }

    private class StoreHandler implements Runnable {

        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            int complete = 0;
            int now = 0;
            for(int i = 0;i<orders.size();i++){
                Order order = orders.get(i);
                if(order.isComplete()){
                    complete++;
                } else {
                    now++;
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
                                    for(int j = 0;j<stores.size();j++){
                                        if(String.valueOf(data.get("name")).equals(stores.get(j).getName())){
                                            temp[0] = false;
                                            Log.d("TEST", "break");
                                            break;
                                        }
                                    }
                                    Log.d("TEST", Boolean.toString(temp[0]));
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
                stores.add(store);
            }
            binding.toolbar13.include.complete.setText(Integer.toString(complete) + "개");
            binding.toolbar13.include.now.setText(Integer.toString(now) + "개");

        }
    }

    private class AdaptHandler implements Runnable{

        @Override
        public void run() {
            LinkedList<Store> list = new LinkedList<>();
            Boolean isAlready;
            for(int i = 0;i<stores.size();i++){
                Store temp1 = stores.get(i);
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
            RecyclerView store_recycle = binding.toolbar13.include.storeRecycle;
            store_recycle.setLayoutManager(new LinearLayoutManager(RiderPageActivity.this));
            store_recycle.setHasFixedSize(true);

            RiderPageAdapter mAdapter = new RiderPageAdapter(list, RiderPageActivity.this);
            mAdapter.setOnItemClickListener(new RiderPageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(RiderPageActivity.this, DeliveryActivity.class);
                    intent.putExtra("delivery", list.get(position));
                    startActivity(intent);
                }
            });
            store_recycle.setAdapter(mAdapter);

        }
    }

    @Override
    protected void onRestart() {
        orderID = new LinkedList<>();
        orders = new LinkedList<>();
        stores = new LinkedList<>();
        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new RiderHandler(), 500);
        hd.postDelayed(new OrderHandler(), 1000);
        hd.postDelayed(new StoreHandler(), 1500);
        hd.postDelayed(new AdaptHandler(), 2000);
        super.onRestart();
    }
}