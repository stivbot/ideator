package com.example.ideator.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Your dashboard will soon be visible here. Visualize your ideas with key indicators and identify the best ones.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}