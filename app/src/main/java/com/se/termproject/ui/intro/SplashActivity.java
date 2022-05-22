package com.se.termproject.ui.intro;

import android.annotation.SuppressLint;
import android.os.Handler;

import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding>{

    @Override
    protected ActivitySplashBinding setViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNextActivity(IntroActivity.class);
                finish();
            }
        }, 2000);
    }
}
