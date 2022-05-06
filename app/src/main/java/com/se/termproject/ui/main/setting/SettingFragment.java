package com.se.termproject.ui.main.setting;

import com.se.termproject.base.BaseFragment;
import com.se.termproject.databinding.FragmentSettingBinding;

public class SettingFragment extends BaseFragment<FragmentSettingBinding> {

    @Override
    protected FragmentSettingBinding setViewBinding() {
        return FragmentSettingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {
    }
}
