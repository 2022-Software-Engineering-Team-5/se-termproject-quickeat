package com.se.termproject.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivityLoginBinding;
import com.se.termproject.ui.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private FirebaseAuth mAuth;
    @Override
    protected ActivityLoginBinding setViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
        mAuth = FirebaseAuth.getInstance();

       //id 입력받기
        //못 받아옴.. 로그 찍어보니까 null이 나온다.
        //String email = binding.loginIdEt.getText().toString().trim();
       //String password = binding.loginPasswordEt.getText().toString();
        String email = "hello@test.com";
        String password = "world1234";
        Log.d("로그인",email);
        Log.d("로그인",password);
        binding.loginSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(email, password);
            }
        });

    }
    //로그인
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        //ID, PW 공란 검사
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //로그인 성공시
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "로그인 성공",
                                    Toast.LENGTH_SHORT).show();
                            //MainActivity로 이동
                            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //startActivity(intent);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //현재 화면에 '로그인 실패' 토스트 문구 노출
                            Toast.makeText(LoginActivity.this, "로그인을 실패하였습니다. 다시 확인해주세요.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //폼 빈칸 체크
    private boolean validateForm() {
        boolean valid = true;

        String email = binding.loginIdEt.getText().toString();
        if (TextUtils.isEmpty(email)) { //아이디 editText가 공란이면
            binding.loginIdEt.setError("아이디를 입력해주세요.");
            valid = false;
        } else {
            binding.loginIdEt.setError(null);
        }

        String password = binding.loginPasswordEt.getText().toString();
        if (TextUtils.isEmpty(password)) { //비밀번호 editText가 공란이면
            binding.loginPasswordEt.setError("비밀번호를 입력해주세요.");
            valid = false;
        } else {
            binding.loginPasswordEt.setError(null);
        }
        return valid;
    }

}
