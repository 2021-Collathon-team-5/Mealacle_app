package com.naca.mealacle.p14;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.naca.mealacle.BR;
import com.naca.mealacle.R;
import com.naca.mealacle.data.Store;
import com.naca.mealacle.databinding.EmployBinding;

public class EmploymentActivity extends AppCompatActivity {

    private EmployBinding binding;
    private EmploymentDialog dialog;

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
        Store store = (Store) intent.getSerializableExtra("store");
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
            Toast.makeText(EmploymentActivity.this, "배달 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private void bind(Store store){
        binding.toolbar14.setVariable(BR.store, store.getName());
        binding.toolbar14.include.setVariable(BR.store_detail, store);
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
