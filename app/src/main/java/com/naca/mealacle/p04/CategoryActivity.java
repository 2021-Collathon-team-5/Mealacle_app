package com.naca.mealacle.p04;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.naca.mealacle.R;
import com.naca.mealacle.databinding.CategoryBinding;

import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {

    private CategoryBinding binding;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private CategoryFragment[] categories = new CategoryFragment[9];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p04_activity_category);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int pos = bundle.getInt("position");
        Log.d("POSITION", Integer.toString(pos));

        fragmentManager = getSupportFragmentManager();

        for(int i = 0;i<categories.length;i++){
            categories[i] = new CategoryFragment();
        }

        Bundle b = new Bundle(1);
        b.putInt("pos", pos);
        categories[pos].setArguments(b);

        transaction = fragmentManager.beginTransaction();
        transaction.replace(binding.toolbar.content.frame.getId(), categories[pos]).commitAllowingStateLoss();

        TabLayout tabs = binding.toolbar.content.tabs;
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Objects.requireNonNull(tabs.getTabAt(pos)).select();
                    }
                }, 100);

        Log.d("TAB SIZE", Integer.toString(tabs.getTabCount()));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Bundle b = new Bundle(1);
                b.putInt("pos", position);
                categories[position].setArguments(b);
                transaction = fragmentManager.beginTransaction();
                transaction.replace(binding.toolbar.content.frame.getId(), categories[position]).commitAllowingStateLoss();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
