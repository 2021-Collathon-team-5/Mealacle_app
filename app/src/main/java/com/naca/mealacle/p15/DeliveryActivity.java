package com.naca.mealacle.p15;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Delivery;
import com.naca.mealacle.data.Order;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.data.User;
import com.naca.mealacle.databinding.DeliveryBinding;
import com.naca.mealacle.p14.EmploymentActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DeliveryActivity extends AppCompatActivity {

    private DeliveryBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Store store;
    private List<String> orderID = new LinkedList<>();
    private List<Order> orders = new LinkedList<>();
    private List<User> users = new LinkedList<>();

    private ImageView foodImage;
    private Bitmap foodBitmap;

    private ImageView mapImage;
    private Bitmap detailBitmap;

    private int clear = 0;
    private int progress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p15_activity_delivery);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar15.toolbar15;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("delivery");

        orderID.add(store.getOrderID());
        for(Store s : store.getRiderList()){
            orderID.add(s.getOrderID());
        }


        CollectionReference orderRef = db.collection("order");
        for(String s :orderID){
            orderRef.document(s).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    orders.add(documentSnapshot.toObject(Order.class));
                }
            });
        }

        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new UserHandler(), 500);
        hd.postDelayed(new AdaptHandler(), 1000);
        bind(store);
    }

    public void bind(Store store){
        binding.toolbar15.include.setVariable(BR.store_delivery, store);

        foodImage = binding.toolbar15.include.image;

        ReviewLoadTask task = new ReviewLoadTask(store.getImage());
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

    private class UserHandler implements Runnable {
        @Override
        public void run() {
            CollectionReference userRef = db.collection("user");
            for(Order o : orders){
                userRef.document(o.getUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        users.add(documentSnapshot.toObject(User.class));
                    }
                });
            }
        }
    }

    private class ReviewLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String urlStr;

        public ReviewLoadTask(String url){
            this.urlStr = url;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try{
                URL url = new URL(urlStr);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }catch (Exception e){}

            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            try {
                if(bitmap != null) {
                    Glide.with(DeliveryActivity.this).load(bitmap).into(foodImage);
                }
                else {
                    Glide.with(DeliveryActivity.this).load(binding.getRoot().getResources().getDrawable(R.drawable.ic_launcher_background)).into(foodImage);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private class AdaptHandler implements Runnable{
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            LinkedList<Delivery> list = new LinkedList<>();
            for(int i = 0;i< orderID.size();i++){
                list.add(new Delivery(orders.get(i), users.get(i), orderID.get(i), store.getProduct()));
            }

            clear = 0;
            progress = 0;
            for(Delivery d : list){
                if(d.getOrder().isComplete()){
                    clear++;
                } else {
                    progress++;
                }
            }

            binding.toolbar15.include.clear.setText(Integer.toString(clear) + "???");
            binding.toolbar15.include.progress.setText(Integer.toString(progress) + "???");


            RecyclerView deliver_recycler = binding.toolbar15.include.deliveryRecycler;
            deliver_recycler.setLayoutManager(new LinearLayoutManager(DeliveryActivity.this));
            deliver_recycler.setHasFixedSize(true);

            DeliveryAdapter mAdapter = new DeliveryAdapter(list);
            mAdapter.setOnItemClickListener(new DeliveryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    switch (v.getId()){
                        case R.id.deliver_complete:
                            TextView textView = (TextView) v;
                            textView.setEnabled(false);
                            textView.setText("??????");
                            db.collection("order").document(list.get(position).getOrderID())
                                    .update("complete", true);

                            clear++;
                            progress--;

                            binding.toolbar15.include.clear.setText(Integer.toString(clear) + "???");
                            binding.toolbar15.include.progress.setText(Integer.toString(progress) + "???");
                            break;
                    }
                }
            });
            deliver_recycler.setAdapter(mAdapter);
        }
    }
}
