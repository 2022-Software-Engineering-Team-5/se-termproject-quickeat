package com.se.termproject.ui.customer.home;

import com.se.termproject.base.java.BaseFragment;
import com.se.termproject.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    @Override
    protected FragmentHomeBinding setViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {
    }
}
