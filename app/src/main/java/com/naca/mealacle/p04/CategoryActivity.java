package com.naca.mealacle.p04;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.naca.mealacle.R;
import com.naca.mealacle.databinding.CategoryBinding;
import com.naca.mealacle.p06.BasketActivity;

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
        String univ = bundle.getString("univ");

        Toolbar toolbar = binding.toolbar.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setVariable(BR.univ, univ);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p05_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.cart: {
                Intent intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
