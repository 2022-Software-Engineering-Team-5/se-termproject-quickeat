package com.se.termproject.ui.customer.history;

import com.se.termproject.base.java.BaseFragment;
import com.se.termproject.databinding.FragmentHistoryBinding;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding> {

    @Override
    protected FragmentHistoryBinding setViewBinding() {
        return FragmentHistoryBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {
    }
}