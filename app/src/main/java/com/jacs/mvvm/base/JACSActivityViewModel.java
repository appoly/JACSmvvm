package com.jacs.mvvm.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class JACSActivityViewModel extends AndroidViewModel {
    public JACSActivityViewModel(@NonNull Application application) {
        super(application);
    }
}
