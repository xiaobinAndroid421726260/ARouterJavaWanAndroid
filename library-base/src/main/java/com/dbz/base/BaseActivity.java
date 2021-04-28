package com.dbz.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.config.Constants;
import com.dbz.base.loadsir.EmptyCallback;
import com.dbz.base.loadsir.ErrorCallback;
import com.dbz.base.loadsir.LoadingCallback;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.utils.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.greenrobot.eventbus.EventBus;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity
        implements CustomAdapt, IBaseView {

    protected DB binding;
    protected VM mViewModel;

    private boolean isFirstLoad = true;
    private boolean isShowedContent = false;

    protected LoadService mLoadService;

    private Bundle savedInstanceState;
    protected int mTheme;
    protected int mThemeColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mTheme = SPStaticUtils.getInt(Constants.theme, R.style.AppTheme_PaleBlue);
        setTheme(mTheme);
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setWindowConfigure();
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        performDataBinding();
        initView();
        initStatusColor();
        initToast();
    }

    private void performDataBinding() {
        mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        if (null != mViewModel) {
            getLifecycle().addObserver(mViewModel);
            if (getBindingVariable() > 0) {
                binding.setVariable(getBindingVariable(), mViewModel);
            }
        }
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
    }

    /**
     * 注册LoadSir
     *
     * @param view 替换视图
     */
    public void setLoadSir(View view) {
        if (mLoadService == null) {
            mLoadService = LoadSir.getDefault()
                    .register(view, (Callback.OnReloadListener) v -> onReloadClick());
        }

    }

    protected abstract int getLayoutId();

    /**
     * 获取参数Variable
     */
    protected abstract int getBindingVariable();

    protected abstract VM getViewModel();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 提供子类方法 在设置布局之前
     */
    protected void setWindowConfigure() {

    }

    /**
     * 失败重试,重新加载事件
     */
    protected void onReloadClick() {

    }

    protected void initStatusColor() {
        if (!SPStaticUtils.getBoolean(Constants.switch_nightMode, false)) {
            mThemeColor = SPStaticUtils.getInt(Constants.color, R.color.colorPrimary);
        } else {
            mThemeColor = R.color.colorPrimary;
        }
        if (getSupportActionBar() != null) {
            if (mThemeColor == R.color.accent_white) {
                setStatusBarColor(mThemeColor, true, true);
            } else {
                setStatusBarColor(mThemeColor);
            }
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(mThemeColor)));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (SPStaticUtils.getBoolean(Constants.nav_bar, true)) {
                getWindow().setNavigationBarColor(getResources().getColor(mThemeColor));
            }
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param color 状态栏颜色 默认状态栏字体颜色白
     */
    public void setStatusBarColor(int color) {
        setStatusBarColor(color, true, false);
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(int color, boolean fits, boolean barDarkFont) {
        ImmersionBar.with(this)
                .statusBarDarkFont(barDarkFont)
                .fitsSystemWindows(fits)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(color)
                .init();
    }

    private void initToast() {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
    }


    protected Bundle getSavedInstanceState() {
        return savedInstanceState;
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
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initData();
            isFirstLoad = false;
        } else {
            if (isNeedReload()) {
                initData();
            }
        }
        initStatusColor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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