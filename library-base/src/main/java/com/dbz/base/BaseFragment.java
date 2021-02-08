package com.dbz.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.loadsir.EmptyCallback;
import com.dbz.base.loadsir.ErrorCallback;
import com.dbz.base.loadsir.LoadingCallback;
import com.dbz.base.viewmodel.BaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;


import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment
        implements CustomAdapt, IBaseView {

    protected DB binding;
    protected VM mViewModel;

    protected View rootView = null;
    private boolean isFirstLoad = true;
    private boolean isShowedContent = false;

    protected LoadService mLoadService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            rootView = binding.getRoot();
        }
        performDataBinding();
        return rootView;
    }

    private void performDataBinding() {
        mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        if (null != mViewModel) {
            getLifecycle().addObserver(mViewModel);
            if (getBindingVariable() > 0) {
                binding.setVariable(getBindingVariable(), mViewModel);
                binding.executePendingBindings();
            }
        }
        binding.setLifecycleOwner(this);
        initView(getArguments());
        if (null != mViewModel){
            mViewModel.mErrorMsg.observe(getViewLifecycleOwner(), this::showFailure);
        }
    }

    /**
     * 获取参数Variable
     */
    protected abstract int getBindingVariable();

    protected abstract int getLayoutId();

    protected abstract VM getViewModel();

    protected abstract void initView(Bundle bundle);

    protected abstract void initData();

    /**
     * 失败重试,重新加载事件
     */
    protected void onReloadClick() {

    }

//    /**
//     * 是否支持页面加载。默认不支持
//     *
//     * @return true表示支持，false表示不支持
//     */
//    protected boolean isSupportLoad() {
//        return true;
//    }

    /**
     * 再次可见时，是否重新请求数据，默认为true
     */
    protected boolean isNeedReload() {
        return false;
    }

    public void setLoadSir(View view) {
        mLoadService = LoadSir.getDefault()
                .register(view, (Callback.OnReloadListener) v -> onReloadClick());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initData();
            isFirstLoad = false;
        } else {
            if (isNeedReload()) {
                initData();
            }
        }
    }

    @Override
    public void showContent() {
        if (null != mLoadService) {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (null != mLoadService) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showEmpty() {
        if (null != mLoadService) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void showFailure(String message) {
        if (null != mLoadService) {
            if (!isShowedContent) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                ToastUtils.showShort(message);
            }
        }
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}