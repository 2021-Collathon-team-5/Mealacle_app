package com.naca.mealacle.p4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.naca.mealacle.R;
import com.naca.mealacle.databinding.CategoryFragmentBinding;

public class CategoryFragment extends Fragment {

    private CategoryFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.p04_fragment_category, container, false);

        View view = binding.getRoot();

        return view;
    }
}
