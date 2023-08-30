package com.example.ideator.ui.ideas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IdeasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public IdeasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ideas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}