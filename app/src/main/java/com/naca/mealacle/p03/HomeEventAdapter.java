package com.naca.mealacle.p03;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.naca.mealacle.R;

import java.util.List;

public class HomeEventAdapter extends PagerAdapter {
    private Context context;
    private List<Integer> dataList;

    public HomeEventAdapter(Context context, List<Integer> list){
        this.context = context;
        this.dataList = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.p03_element_banner, container, false);

        ImageView imageView = view.findViewById(R.id.banner_image);
        Glide.with(view.getContext()).load(dataList.get(position)).into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View) object);
    }



    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public List<Integer> getDataList() {
        return dataList;
    }
}