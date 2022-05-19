package com.se.termproject.ui.intro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;

import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivitySplashBinding;
<<<<<<< HEAD
import com.se.termproject.ui.login.LoginActivity;
import com.se.termproject.ui.main.MainActivity;
=======
import com.se.termproject.ui.admin.MainActivity;
import com.se.termproject.ui.login.LoginActivity;
>>>>>>> upstream/develop

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
<<<<<<< HEAD
                startNextActivity(LoginActivity.class);
=======
                startNextActivity(IntroActivity.class);
>>>>>>> upstream/develop
                finish();
            }
        }, 2000);
    }
}
