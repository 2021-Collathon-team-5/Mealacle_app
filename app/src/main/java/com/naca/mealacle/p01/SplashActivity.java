package com.naca.mealacle.p01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.naca.mealacle.R;
import com.naca.mealacle.p02.UnivSelectActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.p01_activity_splash);

        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new SplashHandler(), 3000);
    }

    private class SplashHandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), UnivSelectActivity.class));
            SplashActivity.this.finish();
        }
    }
}
