package com.se.termproject.ui.customer;

import static com.se.termproject.util.ApplicationClass.USER_ID;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.se.termproject.R;
import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.data.Customer;
import com.se.termproject.data.Review;
import com.se.termproject.databinding.ActivityMainBinding;
import com.se.termproject.ui.customer.home.HomeFragment;
import com.se.termproject.ui.customer.setting.SettingFragment;
import com.se.termproject.ui.customer.history.HistoryFragment;
import com.se.termproject.util.ApplicationClass;
import com.se.termproject.util.SharedPreferencesManagerKt;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private static Fragment homeFragment, historyFragment, settingFragment;

    // firebase
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mCustomersReference;

    @Override
    protected ActivityMainBinding setViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
        user = FirebaseAuth.getInstance().getCurrentUser();

    initReference();
        initData();

        // fragment
        homeFragment = new HomeFragment();
        historyFragment = new HistoryFragment();
        settingFragment = new SettingFragment();

        // initial setting (with HomeFragment)
        replaceFragment(homeFragment);


        initBottomNavigationView();
        initClickListener();
    }

    private void initReference() {
        mDatabase = FirebaseDatabase.getInstance();
        mCustomersReference = mDatabase.getReference("customers");
    }

    private void initData() {
        USER_ID = SharedPreferencesManagerKt.getUserId();
        Customer customer = new Customer(user.getUid(), user.getDisplayName(), user.getEmail(), new Review());
        mCustomersReference.child(USER_ID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Customer.class) == null) {
                    mCustomersReference.child(USER_ID).setValue(customer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void initBottomNavigationView() {
        binding.mainBnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.main_bnv_history:
                        replaceFragment(historyFragment);
                        return true;
                    case R.id.main_bnv_setting:
                        replaceFragment(settingFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void initClickListener() {
        binding.mainHomeFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MenuItem home = binding.mainBnv.getMenu().getItem(0);
                home.setChecked(true);
                replaceFragment(homeFragment);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
