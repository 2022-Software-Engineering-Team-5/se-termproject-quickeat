package com.se.termproject.base.java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    public final String TAG = getClass().getSimpleName();
    private VB binding;

//    public abstract Fragment newInstance();

    protected abstract VB setViewBinding();

    public void initViewBinding() {
        binding = setViewBinding();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.initViewBinding();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initAfterViewBinding();
    }

    protected abstract void initAfterViewBinding();

    public void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
