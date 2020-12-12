package com.best.movie.note.ui.splash;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashActivityViewModel extends ViewModel {

    private boolean isLock = false;

    private MutableLiveData<Boolean> isLockLiveData = new MutableLiveData<>();

    {
        this.isLockLiveData.setValue(false);
        Log.d("check", "init ViewModel");
    }

    public MutableLiveData<Boolean> getIsLock() {
        isLockLiveData.setValue(isLock);
        return isLockLiveData;
    }

    public void setLock(boolean lock) {
        isLock = lock;
        isLockLiveData.setValue(isLock);
    }

}
