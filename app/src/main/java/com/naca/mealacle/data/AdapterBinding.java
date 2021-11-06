package com.naca.mealacle.data;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

public class AdapterBinding {
    @BindingAdapter("android:setImage")
    public static void loadImage(View view, int imageID){
        ImageView imageView = (ImageView) view;
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), imageID));
    }
}
