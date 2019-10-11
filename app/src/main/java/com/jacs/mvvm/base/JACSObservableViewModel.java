package com.jacs.mvvm.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;

public abstract class JACSObservableViewModel extends JACSViewModel implements Observable {

    public JACSObservableViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {}

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {}
}
