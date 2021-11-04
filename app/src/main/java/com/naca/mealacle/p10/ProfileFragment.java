package com.naca.mealacle.p10;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.mealacle.R;
import com.naca.mealacle.databinding.ProfileToolbarBinding;
import com.naca.mealacle.p09.NotifyActivity;
import com.naca.mealacle.p11.OrderedHistoryActivity;
import com.naca.mealacle.p12.RiderActivity;

public class ProfileFragment extends Fragment {

    private ProfileToolbarBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.p10_app_toolbar_profile, container, false);

        View view = binding.getRoot();

        RecyclerView inform_recycler = binding.include.informRecycler;
        inform_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        inform_recycler.setHasFixedSize(true);

        String[] inform = {"공지사항", "이벤트", "고객센터", "라이더 관리", "환경설정", "약관 및 정책"};

        ProfileAdapter mAdapter = new ProfileAdapter(inform);
        mAdapter.setOnItemClickListener(new ProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(position == 3){
                    Intent intent = new Intent(getContext(), RiderActivity.class);
                    startActivity(intent);
                }
            }
        });
        inform_recycler.setAdapter(mAdapter);

        binding.include.orderedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderedHistoryActivity.class);
                startActivity(intent);
            }
        });

        binding.include.alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotifyActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
