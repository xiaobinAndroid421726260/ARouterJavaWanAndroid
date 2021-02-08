package com.dbz.system.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.system.NaviBean;

import java.util.List;

public class SystemTwoViewModel extends BaseViewModel {

    public final MutableLiveData<List<NaviBean.DataBean>> mDataBean = new MutableLiveData<>();


    public void getNaviJson(){
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getNaviJson(), new BaseObserver<NaviBean>() {
                    @Override
                    public void onSucceed(NaviBean result) {
                        if (result.getErrorCode() == 0){
                            mDataBean.setValue(result.getData());
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                            mErrorMsg.setValue(result.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.showShort(msg);
                        mErrorMsg.setValue(msg);
                    }
                });
    }
}