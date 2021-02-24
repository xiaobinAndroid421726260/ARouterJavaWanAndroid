package com.dbz.setting;

import android.graphics.Color;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dbz.base.BaseActivity;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.setting.databinding.ActivitySettingsBinding;

@Route(path = RouterActivityPath.Setting.PAGER_SETTING)
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, BaseViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("设置");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_black);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        binding.llTheme.setOnClickListener(v -> ARouter.getInstance().build(RouterActivityPath.Theme.PAGER_THEME).navigation());
    }

    @Override
    protected void initData() {

    }
}