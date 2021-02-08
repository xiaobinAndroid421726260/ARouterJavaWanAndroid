package com.dbz.demo;

import androidx.lifecycle.MutableLiveData;

import com.dbz.base.viewmodel.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>();


}