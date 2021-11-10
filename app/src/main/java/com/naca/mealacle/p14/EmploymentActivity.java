package com.naca.mealacle.p14;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.data.User;
import com.naca.mealacle.databinding.EmployBinding;
import com.naca.mealacle.p01.SplashActivity;
import com.naca.mealacle.p02.UserInputActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class EmploymentActivity extends AppCompatActivity {

    private EmployBinding binding;
    private EmploymentDialog dialog;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Bitmap bitmap;
    private ImageView imageView;
    private Store store;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p14_activity_employ);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar14.toolbar14;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("store");
        Log.d("NAME", store.getName());
        bind(store);

        CheckBox checkBox = binding.toolbar14.include.checkbox;
        TextView addDeliver = binding.toolbar14.include.addDeliver;

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    addDeliver.setEnabled(true);
                } else {
                    addDeliver.setEnabled(false);
                }
            }
        });

        addDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });


    }

    public void Dialog(){
        dialog = new EmploymentDialog(EmploymentActivity.this, leftListener, rightListener);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Map<String, Object> data = new HashMap<>();
            List<String> orderID = new LinkedList<>();
            orderID.add(store.getOrderID());
            for(Store s : store.getRiderList()){
                orderID.add(s.getOrderID());
                Log.d("ORDERID", s.getOrderID());
            }
            data.put("orderid", orderID);
            data.put("userid", SplashActivity.userID);
            db.collection("rider").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    db.collection("order").document(store.getOrderID()).update("assign", true);
                    for(Store s : store.getRiderList()){
                        DocumentReference orderRef = db.collection("order").document(s.getOrderID());
                        orderRef.update("assign", true);
                        orderRef.update("riderID", documentReference.getId());
                    }
                    db.collection("user").document(SplashActivity.userID)
                            .update("riderID", FieldValue.arrayUnion(documentReference.getId()));
                    UserInputActivity.user.getRiderID().add(documentReference.getId());
                }
            });
            db.collection("user").document(SplashActivity.userID)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            UserInputActivity.user = documentSnapshot.toObject(User.class);
                        }
                    });
            Toast.makeText(EmploymentActivity.this, "배달 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            finish();
        }
    };

    private void bind(Store store){
        binding.toolbar14.setVariable(BR.store, store.getName());
        binding.toolbar14.include.setVariable(BR.store_detail, store);
        imageView = binding.toolbar14.include.image;
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(store.getImage());

                    HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream is = connect.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
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
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
