package com.jacs.mvvm.base;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

public abstract class JACSViewModel extends AndroidViewModel implements Serializable {

    protected MutableLiveData<Integer> navigationActionLiveData = new MutableLiveData<>();
    protected Bundle bundle = new Bundle();

    public JACSViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    protected void init() { }

    protected void performAction(Integer action, Bundle bundle) {
        this.bundle = bundle;
        navigationActionLiveData.setValue(action);
    }

}
