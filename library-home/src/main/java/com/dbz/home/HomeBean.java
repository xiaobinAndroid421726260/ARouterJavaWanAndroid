package com.dbz.home;

import com.dbz.network.retrofit.bean.home.TopBean;

import java.util.List;

public class HomeBean {

    private int errorCode;
    private String errorMsg;
    private boolean isRefresh;
    private List<TopBean.DataBean> data;


    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<TopBean.DataBean> getData() {
        return data;
    }

    public void setData(List<TopBean.DataBean> data) {
        this.data = data;
    }
}