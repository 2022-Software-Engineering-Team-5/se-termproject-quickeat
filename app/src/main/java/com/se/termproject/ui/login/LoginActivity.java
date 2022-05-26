package com.se.termproject.ui.login;

import static com.se.termproject.util.ApplicationClass.USER_ID;
import static com.se.termproject.util.SharedPreferencesManagerKt.saveUserId;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.SharedPreferences;

import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.ktx.Firebase;
import com.google.rpc.context.AttributeContext;
import com.se.termproject.R;
import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivityLoginBinding;
import com.se.termproject.ui.shopkeeper.CheckActivity;
import com.se.termproject.util.SharedPreferencesManagerKt;

import java.util.Objects;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private static final String TAG = "ACT/LOGIN";
    private static final int RC_SIGN_IN = 1000;

    private int mode = 0;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected ActivityLoginBinding setViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
        mAuth = FirebaseAuth.getInstance();

        initGoogleLogin();
        initMode();
        initClickListener();
    }

    private void initGoogleLogin() {
        // Google login API
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("698089618576-5ok4me4pls0datpcpu4t3h37jsipik18.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseGoogleAuth(account);
            } catch (ApiException e) {
                Log.d(TAG, "로그인 실패: " + e);
            }
        }
    }

    private void firebaseGoogleAuth(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                            saveUserId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                            USER_ID = SharedPreferencesManagerKt.getUserId();

                            // activity 전환
                            if (mode == 0) {
                                // customer mode
                                startNextActivity(com.se.termproject.ui.customer.MainActivity.class);
                            } else {
                                // admin mode
                                startNextActivity(CheckActivity.class);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }
                });
    }

    // customer mode인지 shopkeeper mode인지
    private void initMode() {
        SharedPreferences spf = getSharedPreferences("mode", 0);
        mode = spf.getInt("mode", 0);
    }

    // click listener
    private void initClickListener() {

        // login 버튼 클릭 시
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "loginBtn/onClick");

                startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
            }
        });

        // lotout 버튼 클릭 시
        binding.loginLogoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setMessage("로그아웃 하시겠습니까?").setCancelable(false)
                        .setPositiveButton("네",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        logout();
                                    }
                                })
                        .setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                AlertDialog alert = alertDialog.create();
                alert.setTitle("로그아웃");
                alert.show();
            }
        });
    }

    private void logout() {
        saveUserId("");
        mAuth.signOut();
        mGoogleSignInClient.signOut();
    }
}
