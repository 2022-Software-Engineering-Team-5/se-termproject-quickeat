package com.se.termproject.ui.customer;

import android.annotation.SuppressLint;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.se.termproject.R;
import com.se.termproject.base.java.BaseActivity;
import com.se.termproject.databinding.ActivityMainBinding;
import com.se.termproject.ui.customer.bookmark.BookmarkFragment;
import com.se.termproject.ui.customer.history.HistoryFragment;
import com.se.termproject.ui.customer.home.HomeFragment;
import com.se.termproject.ui.customer.setting.SettingFragment;
import com.se.termproject.ui.intro.IntroActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private static Fragment homeFragment, bookmarkFragment, historyFragment, settingFragment;

    @Override
    protected ActivityMainBinding setViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterBinding() {
        // fragment
        homeFragment = new HomeFragment();
        bookmarkFragment = new BookmarkFragment();
        historyFragment = new HistoryFragment();
        settingFragment = new SettingFragment();

        // initial setting (with HomeFragment)
        replaceFragment(homeFragment);

        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        binding.mainBnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.main_bnv_home:
                        replaceFragment(homeFragment);
                        return true;
                    case R.id.main_bnv_bookmark:
                        replaceFragment(bookmarkFragment);
                        return true;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startNextActivityWithClear(IntroActivity.class);
        finish();
    }
}