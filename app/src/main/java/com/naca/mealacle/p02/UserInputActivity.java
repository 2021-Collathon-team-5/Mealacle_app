package com.naca.mealacle.p02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naca.mealacle.R;
import com.naca.mealacle.data.PreferenceManager;
import com.naca.mealacle.data.User;
import com.naca.mealacle.databinding.UserBinding;
import com.naca.mealacle.p01.SplashActivity;
import com.naca.mealacle.p03.HomeActivity;

public class UserInputActivity extends AppCompatActivity {

    private UserBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p02_activity_user);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        User temp = (User) intent.getSerializableExtra("user");

        Toolbar toolbar = binding.toolbar021.toolbar021;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        EditText name = binding.toolbar021.p2Content.name;
        EditText address = binding.toolbar021.p2Content.address;
        EditText email = binding.toolbar021.p2Content.email;
        EditText phone = binding.toolbar021.p2Content.phone;

        binding.toolbar021.p2Content.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || address.getText().toString().equals("") ||
                        email.getText().toString().equals("") || phone.getText().toString().equals("")){
                    Toast toast = Toast.makeText(UserInputActivity.this.getApplicationContext(),
                            "모든 항목을 채워주세요", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    user = new User(temp.getUniv(), name.getText().toString(),
                            address.getText().toString(), email.getText().toString(), phone.getText().toString());
                    db.collection("user")
                            .add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("ADD", "DocumentSnapshot written with ID: " + documentReference.getId());
                            PreferenceManager.setString(UserInputActivity.this, "id", documentReference.getId());
                            SplashActivity.userID =documentReference.getId();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FAILURE", "Error adding document", e);
                        }
                    });
                    Intent intent = new Intent(UserInputActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
