package com.se.termproject.ui.main.home;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.se.termproject.base.java.BaseFragment;
import com.se.termproject.databinding.FragmentHomeBinding;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    public class Login{
        private String id;
        private String password;

        public Login() {}

        public Login(String id, String password) {}
    }

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected FragmentHomeBinding setViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {

        db.collection("users").document("example")
                .update(
                  "id", "change3"
                );



    }
}
