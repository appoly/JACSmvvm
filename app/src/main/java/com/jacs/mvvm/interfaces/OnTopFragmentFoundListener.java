package com.jacs.mvvm.interfaces;

import androidx.fragment.app.Fragment;

import com.jacs.mvvm.base.JACSFragment;


public interface OnTopFragmentFoundListener {
    void topFragmentFound(JACSFragment fragment);
    void wrongFragmentFound(Fragment fragment);
}
