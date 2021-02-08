package com.dbz.home;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.BaseObserver;
import com.dbz.network.retrofit.RetrofitFactory;
import com.dbz.network.retrofit.api.Api;
import com.dbz.network.retrofit.bean.BannerBean;
import com.dbz.network.retrofit.bean.home.ArticleBean;
import com.dbz.network.retrofit.bean.home.TopBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HomeViewModel extends BaseViewModel {

    public final MutableLiveData<HomeBean> mHomeBean = new MutableLiveData<>();
    public final MutableLiveData<List<String>> mBannerUrl = new MutableLiveData<>();
    private final List<TopBean.DataBean> mDataBeans = new ArrayList<>();
    private final List<String> bannerUrl = new ArrayList<>();

    public void getBannerJson() {
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getBannerJson(), new BaseObserver<BannerBean>() {
                    @Override
                    public void onSucceed(BannerBean result) {
                        if (result.getErrorCode() == 0) {
                            setBannerUrl(result.getData());
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.showShort(msg);
                        mErrorMsg.setValue(msg);
                    }
                });
    }

    private void setBannerUrl(List<BannerBean.DataBean> dataBeans) {
        if (null != dataBeans && !dataBeans.isEmpty()) {
            Observable.fromIterable(dataBeans).subscribe(new Observer<BannerBean.DataBean>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    bannerUrl.clear();
                    if (null != mBannerUrl.getValue()) {
                        mBannerUrl.getValue().clear();
                    }
                }

                @Override
                public void onNext(@NonNull BannerBean.DataBean dataBean) {
                    bannerUrl.add(dataBean.getImagePath());
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                    mBannerUrl.setValue(bannerUrl);
                }
            });
        }
    }

    public void getTopJson(int page, boolean isRefresh) {
        RetrofitFactory.getInstance()
                .subscribe(RetrofitFactory.getInstance()
                        .create(Api.class).getTopJson(), new BaseObserver<TopBean>() {
                    @Override
                    public void onSucceed(TopBean result) {
                        if (result.getErrorCode() == 0) {
                            Observable.fromIterable(result.getData()).subscribe(new Observer<TopBean.DataBean>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    mDataBeans.clear();
                                }

                                @Override
                                public void onNext(@NonNull TopBean.DataBean dataBean) {
                                    dataBean.setTop(1);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    if (isRefresh) {
                                        mDataBeans.clear();
                                        mDataBeans.addAll(result.getData());
                                    }
                                    getArticleJson(page, isRefresh, mDataBeans);
                                }
                            });
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.showShort(msg);
                        mErrorMsg.setValue(msg);
                    }
                });
    }

    private void getArticleJson(int page, boolean isRefresh, final List<TopBean.DataBean> dataBeans) {
        RetrofitFactory.getInstance().subscribe(RetrofitFactory.getInstance()
                        .create(Api.class)
                        .getArticleJson(page),
                new BaseObserver<ArticleBean>() {
                    @Override
                    public void onSucceed(ArticleBean result) {
                        if (result.getErrorCode() == 0) {
                            dataBeans.addAll(result.getData().getDatas());
                            HomeBean homeBean = new HomeBean();
                            homeBean.setRefresh(isRefresh);
                            homeBean.setData(new ArrayList<>(dataBeans));
                            mHomeBean.setValue(homeBean);
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
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
        getBannerJson();
    }
}