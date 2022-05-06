package com.se.termproject.ui.main.home;

import com.se.termproject.base.BaseFragment;
import com.se.termproject.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

//    public static Fragment newInstance() {
//        return new HomeFragment();
//    }

    @Override
    protected FragmentHomeBinding setViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {
    }
}
