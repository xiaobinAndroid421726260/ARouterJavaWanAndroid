package com.dbz.wechat.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.wechat.WechatFragmentBean;

import java.util.ArrayList;
import java.util.List;

public class WechatViewPagerViewModel extends BaseViewModel {

    public final MutableLiveData<WechatFragmentBean> mDataBean = new MutableLiveData<>();
    public final List<WechatFragmentBean.DataBean.DatasBean> datasBeans = new ArrayList<>();

    public void getUserArticleJson(int user_id, int page, boolean isRefresh) {
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getUserArticleJson(user_id, page), new BaseObserver<WechatFragmentBean>() {
                    @Override
                    public void onSucceed(WechatFragmentBean result) {
                        if (result.getErrorCode() == 0) {
                            mDataBean.setValue(result);
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