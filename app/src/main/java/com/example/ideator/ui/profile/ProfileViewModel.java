package com.example.ideator.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Your profile will soon be visible here. It will be visible by incubators in order to know more about you.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}