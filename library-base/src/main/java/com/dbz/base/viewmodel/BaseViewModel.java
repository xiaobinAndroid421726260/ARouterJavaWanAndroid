package com.dbz.base.viewmodel;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel implements LifecycleObserver {


    /**
     * 加载出错
     */
    public final MutableLiveData<String> mErrorMsg = new MutableLiveData<>("加载出错");

    /**
     * 重新加载数据。没有网络，点击重试时回调
     */
    public void reloadData() {

    }

}