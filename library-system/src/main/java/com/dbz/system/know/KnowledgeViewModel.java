package com.dbz.system.know;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.system.SystemJsonBean;

public class KnowledgeViewModel extends BaseViewModel {

    public final MutableLiveData<SystemJsonBean.DataBean> mDataBean = new MutableLiveData<>();


    public void getTreeJsonCid(int page, int cid){
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getTreeJsonCid(page, cid), new BaseObserver<SystemJsonBean>() {
                    @Override
                    public void onSucceed(SystemJsonBean result) {
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