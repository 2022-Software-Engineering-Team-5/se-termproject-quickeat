package com.se.termproject.ui.main.bookmark;

import com.se.termproject.base.java.BaseFragment;
import com.se.termproject.databinding.FragmentBookmarkBinding;

public class BookmarkFragment extends BaseFragment<FragmentBookmarkBinding> {

    @Override
    protected FragmentBookmarkBinding setViewBinding() {
        return FragmentBookmarkBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {
    }
}
