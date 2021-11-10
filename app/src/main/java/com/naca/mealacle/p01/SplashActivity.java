package com.naca.mealacle.p01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.naca.mealacle.R;
import com.naca.mealacle.data.PreferenceManager;
import com.naca.mealacle.data.User;
import com.naca.mealacle.p02.UnivSelectActivity;
import com.naca.mealacle.p02.UserInputActivity;
import com.naca.mealacle.p03.HomeActivity;

public class SplashActivity extends Activity {

    public static String userID = "";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.p01_activity_splash);

        Handler hd = new Handler(Looper.getMainLooper());
        userID = PreferenceManager.getString(this, "id");
        if(userID.equals("")){
            hd.postDelayed(new SplashHandler1(), 3000);
        } else {
            DocumentReference docRef = db.collection("user").document(userID);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserInputActivity.user = documentSnapshot.toObject(User.class);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    hd.postDelayed(new SplashHandler1(), 3000);
                }
            });
            hd.postDelayed(new SplashHandler2(), 3000);
        }
    }

    private class SplashHandler1 implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), UnivSelectActivity.class));
            SplashActivity.this.finish();
        }
    }

    private class SplashHandler2 implements Runnable {
        public void run() {
            Intent intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
