package com.dbz.square;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.square.SquareBean;


public class SquareViewModel extends BaseViewModel {

    public final MutableLiveData<SquareBean.DataBean> mDataBeans = new MutableLiveData<>();

    public void getSquareModel(int page) {
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class)
                        .getUserArticleJson(page), new BaseObserver<SquareBean>() {
                    @Override
                    public void onSucceed(SquareBean result) {
                        if (result.getErrorCode() == 0) {
                            mDataBeans.setValue(result.getData());
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
        getSquareModel(0);
    }
}