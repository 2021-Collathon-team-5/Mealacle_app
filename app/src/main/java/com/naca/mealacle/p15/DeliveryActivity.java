package com.naca.mealacle.p15;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.mealacle.R;
import com.naca.mealacle.databinding.DeliveryBinding;

public class DeliveryActivity extends AppCompatActivity {

    private DeliveryBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p15_activity_delivery);
        binding.setLifecycleOwner(this);
    }
}
