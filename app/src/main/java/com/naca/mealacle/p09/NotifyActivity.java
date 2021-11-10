package com.naca.mealacle.p09;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

        Toolbar toolbar = binding.toolbar09.toolbar09;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinkedList<Alert> list = new LinkedList<>();

        list.add(new Alert("주문이 완료되었습니다.", "주문은 오전 9시와 오후 9시, 하루에 2번에 걸쳐 배송됩니다."));
        list.add(new Alert("Mealacle 이벤트", "Mealacle앱을 사용하고 플레이 스토어에 후기를 남겨주면 추첨을 통해 상품을 드립니다!!"));
        list.add(new Alert("Mealacle 정식 오픈!", "코로나 시대. 이제 하루에 2번 편하게 코로나 밀키트 세트를 받아보세요 :)"));


        RecyclerView notify_recycler = binding.toolbar09.include.notifyRecycler;
        notify_recycler.setLayoutManager(new LinearLayoutManager(this));
        notify_recycler.setHasFixedSize(true);

        NotifyAdapter mAdapter = new NotifyAdapter(list);
        notify_recycler.setAdapter(mAdapter);
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

