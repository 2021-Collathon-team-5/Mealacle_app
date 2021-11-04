package com.naca.mealacle.p14;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.EmployBinding;

public class EmploymentActivity extends AppCompatActivity {

    private EmployBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p14_activity_employ);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        Store store = (Store) intent.getSerializableExtra("store");
        Log.d("NAME", store.getName());
        bind(store);

        binding.toolbar.include.addDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void bind(Store store){
        binding.toolbar.include.setVariable(BR.name_store, store.getName());
        binding.toolbar.include.setVariable(BR.product_store, store.getProduct());
        binding.toolbar.include.setVariable(BR.cost_store, store.getCost());
        binding.toolbar.include.setVariable(BR.time_store, store.getTime());
        binding.toolbar.include.setVariable(BR.address_store, store.getAddress());
    }
}
