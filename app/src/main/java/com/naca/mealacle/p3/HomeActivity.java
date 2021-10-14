package com.naca.mealacle.p3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.naca.mealacle.R;
import com.naca.mealacle.databinding.HomeActivityBinding;

public class HomeActivity extends AppCompatActivity {

    private HomeActivityBinding binding;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p03_activity_home);
        binding.setLifecycleOwner(this);

        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(binding.frame.getId(), homeFragment).commitAllowingStateLoss();
    }
}
