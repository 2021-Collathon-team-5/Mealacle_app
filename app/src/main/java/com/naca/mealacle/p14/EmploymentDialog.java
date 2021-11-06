package com.naca.mealacle.p14;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.naca.mealacle.R;
import com.naca.mealacle.databinding.DialogBinding;

public class EmploymentDialog extends Dialog {

    private Context context;
    private DialogBinding binding;
    private View.OnClickListener leftListener;
    private View.OnClickListener rightListener;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.p14_dialog, null, false);

//        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
//        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        lpWindow.dimAmount = 0.8f;
//        getWindow().setAttributes(lpWindow);

        setContentView(binding.getRoot());

        binding.left.setOnClickListener(leftListener);
        binding.right.setOnClickListener(rightListener);
    }

    public EmploymentDialog(Context context, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context);
        this.context = context;
        this.leftListener = leftListener;
        this.rightListener = rightListener;
    }
}



