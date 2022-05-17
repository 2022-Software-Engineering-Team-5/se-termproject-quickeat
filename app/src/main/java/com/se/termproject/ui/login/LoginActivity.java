package com.se.termproject.ui.login;

import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected ActivityLoginBinding setViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
    }
}
