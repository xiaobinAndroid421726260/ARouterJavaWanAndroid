package com.dbz.project.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.project.ProjectBean;
import com.dbz.network.retrofit.utils.LogUtils;


public class ProjectListViewModel extends BaseViewModel {

    public final MutableLiveData<ProjectBean.DataBean> mDataBean = new MutableLiveData<>();


    public void getProjectCidJson(int page, int cid) {
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getProjectCidJson(page, cid), new BaseObserver<ProjectBean>() {
                    @Override
                    public void onSucceed(ProjectBean result) {
                        if (result.getErrorCode() == 0) {
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