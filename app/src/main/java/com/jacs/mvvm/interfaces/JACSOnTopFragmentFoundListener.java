package com.jacs.mvvm.interfaces;

import androidx.fragment.app.Fragment;

import com.jacs.mvvm.base.JACSBaseFragment;


public interface JACSOnTopFragmentFoundListener {
    void topFragmentFound(JACSBaseFragment fragment);
    void wrongFragmentFound(Fragment fragment);
}
