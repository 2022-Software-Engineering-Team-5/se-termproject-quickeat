package com.se.termproject.ui.login;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected ActivityLoginBinding setViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
        // onCreate
        //binding.loginAtLoginTv.


    }
}
