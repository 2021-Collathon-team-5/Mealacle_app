package com.naca.mealacle.p03;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.naca.mealacle.R;
import com.naca.mealacle.databinding.HomeActivityBinding;
import com.naca.mealacle.p06.BasketActivity;
import com.naca.mealacle.p10.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private HomeActivityBinding binding;
    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p03_activity_home);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();

        homeFragment.setArguments(b);

        transaction = fragmentManager.beginTransaction();
        transaction.replace(binding.frame.getId(), homeFragment).commitAllowingStateLoss();

        initNavigationBar();
    }

    private void initNavigationBar() {
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.home:{
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(binding.frame.getId(), homeFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.profile:{
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(binding.frame.getId(), profileFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.cart:{
                        Intent intent = new Intent(HomeActivity.this, BasketActivity.class);
                        startActivity(intent);
                        return false;
                    }
                }
                return false;
            }
        });
    }
}
