package com.dbz.wechat.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.wechat.WechatBean;
import com.dbz.network.retrofit.utils.LogUtils;

import java.util.List;

public class WechatViewModel extends BaseViewModel {

    public final MutableLiveData<List<WechatBean.DataBean>> mDataBean = new MutableLiveData<>();

    public void getWechatArticle(){
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getWechatArticle(), new BaseObserver<WechatBean>() {
                    @Override
                    public void onSucceed(WechatBean result) {
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

    @Override
    public void reloadData() {
        super.reloadData();
        getWechatArticle();
    }
}