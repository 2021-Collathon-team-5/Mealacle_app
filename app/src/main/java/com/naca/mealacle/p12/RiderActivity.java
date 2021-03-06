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
    private RiderAdapter mAdapter;
    private RecyclerView store_recycle;

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

        store_recycle = binding.toolbar12.include.storeRecycle;
        store_recycle.setLayoutManager(new LinearLayoutManager(RiderActivity.this));
        store_recycle.setHasFixedSize(true);

        mAdapter = new RiderAdapter(storeList, this);
        mAdapter.setOnItemClickListener(new RiderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(RiderActivity.this, EmploymentActivity.class);
                intent.putExtra("store", storeList.get(position));
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


        updateData();
    }

    public void updateData(){

        foodIDList.clear();
        orderList.clear();
        orderID.clear();
        storeList.clear();

        mAdapter.notifyDataSetChanged();

        db.collection("food").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                foodIDList.add(doc.getId());
                            }
                        }

                        for(int i = 0;i<foodIDList.size();i++){
                            int finalI = i;
                            db.collection("order").whereEqualTo("foodID", foodIDList.get(i)).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                idList.add(foodIDList.get(finalI));
                                                for(QueryDocumentSnapshot doc : task.getResult()){
                                                    Order order = doc.toObject(Order.class);
                                                    String orderID = doc.getId();
                                                    Log.e("ID", Boolean.toString(!order.isReady() || order.isAssign()));

                                                    if(!order.isReady() || order.isAssign()){
                                                        continue;
                                                    }
                                                    Store store = new Store();
                                                    final Food[] food = new Food[1];
                                                    db.collection("food").document(order.getFoodID()).get()
                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                    if(task.isSuccessful()){
                                                                        DocumentSnapshot doc = task.getResult();
                                                                        Map<String, Object> data = doc.getData();
                                                                        food[0] = new Food(doc.getId(),
                                                                                String.valueOf(data.get("category")),
                                                                                String.valueOf(data.get("description")),
                                                                                String.valueOf(((List<String>)data.get("image")).get(0)),
                                                                                String.valueOf(data.get("name")),
                                                                                (List<HashMap<String, Object>>) data.get("options"),
                                                                                String.valueOf(data.get("origin")),
                                                                                Long.parseLong(String.valueOf(data.get("price"))),
                                                                                (HashMap<String, Object>) data.get("seller"));
                                                                        store.setProduct(String.valueOf(data.get("name")));
                                                                        store.setProductID(food[0].getProductID());
                                                                        store.setImage(String.valueOf(((List<String>)data.get("image")).get(0)));
                                                                    }

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
                                                                                    store.setOrderID(orderID);
                                                                                    store.setCount(order.getCount());

                                                                                    storeList.add(store);
                                                                                    Log.e("notify", "true");
                                                                                    mAdapter.notifyDataSetChanged();
                                                                                }
                                                                            });
                                                                }
                                                            });
                                                }
                                            } else {
                                                Log.e("error", "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        }

                    }
                });

        binding.toolbar12.include.progress.setText("???????????? ?????? : " + UserInputActivity.user.getRiderID().size() + "???");
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestart() {
        updateData();
        super.onRestart();
    }
}
