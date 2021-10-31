package com.naca.mealacle.p09;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.data.Alert;
import com.naca.mealacle.databinding.NotifyBinding;

import java.util.LinkedList;

public class NotifyActivity extends AppCompatActivity {

    private NotifyBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p09_activity_alert);
        binding.setLifecycleOwner(this);

        LinkedList<Alert> list = new LinkedList<>();

        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<3;i++){
            sb.append("월남쌈 쿠킹박스 밀키트");
            list.add(new Alert(sb.toString(), sb.toString()));
        }

        RecyclerView notify_recycler = binding.toolbar.include.notifyRecycler;
        notify_recycler.setLayoutManager(new LinearLayoutManager(this));
        notify_recycler.setHasFixedSize(true);

        NotifyAdapter mAdapter = new NotifyAdapter(list);
        notify_recycler.setAdapter(mAdapter);
    }
}
