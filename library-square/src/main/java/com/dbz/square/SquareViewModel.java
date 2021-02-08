package com.dbz.square;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.square.SquareBean;
import com.dbz.network.retrofit.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class SquareViewModel extends BaseViewModel {

    public final MutableLiveData<SquareBean> mDataBean = new MutableLiveData<>();
    private final List<SquareBean.DataBean.DatasBean> dataBeans = new ArrayList<>();

    public void getSquareModel(int page, boolean isRefresh) {
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class)
                        .getUserArticleJson(page), new BaseObserver<SquareBean>() {
                    @Override
                    public void onSucceed(SquareBean result) {
                        if (result.getErrorCode() == 0) {
                            if (isRefresh) {
                                dataBeans.clear();
                            }
                            dataBeans.addAll(result.getData().getDatas());
                            SquareBean squareBean = new SquareBean();
                            squareBean.setRefresh(isRefresh);
                            squareBean.setData(result.getData());
                            squareBean.getData().setDatas(new ArrayList<>(dataBeans));
                            mDataBean.setValue(squareBean);
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
        getSquareModel(0, true);
    }
}