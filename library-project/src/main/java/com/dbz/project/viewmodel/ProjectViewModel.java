package com.dbz.project.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.project.ProjectTreeBean;

import java.util.List;

public class ProjectViewModel extends BaseViewModel {

    public final MutableLiveData<List<ProjectTreeBean.DataBean>> mDataBean = new MutableLiveData<>();


    public void getProjectTreeJson(){
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getProjectTreeJson(), new BaseObserver<ProjectTreeBean>() {
                    @Override
                    public void onSucceed(ProjectTreeBean result) {
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
        getProjectTreeJson();
    }
}