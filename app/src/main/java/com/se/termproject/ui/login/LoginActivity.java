package com.se.termproject.ui.login;

import android.content.SharedPreferences;
import android.view.View;

import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private int mode = 0;

    @Override
    protected ActivityLoginBinding setViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
        initMode();
        initClickListener();
    }

    // customer mode인지 admin mode인지
    private void initMode() {
        SharedPreferences spf = getSharedPreferences("mode", 0);
        mode = spf.getInt("mode", 0);
    }

    // click listener
    private void initClickListener() {

        // login button 클릭 시
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 여기에 login 기능 구현

                // login 기능 구현 뒤 activity 전환
                if(mode == 0) {
                    // customer mode
                    startNextActivity(com.se.termproject.ui.customer.MainActivity.class);
                } else {
                    // admin mode
                    startNextActivity(com.se.termproject.ui.admin.MainActivity.class);
                }

                finish();
            }
        });
    }
}
