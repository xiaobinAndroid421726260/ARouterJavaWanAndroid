package com.dbz.setting;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.BaseActivity;
import com.dbz.base.ViewColorUtils;
import com.dbz.base.config.Constants;
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
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        binding.llTheme.setOnClickListener(v -> {
            if (SPStaticUtils.getBoolean(Constants.switch_nightMode)) {
                ToastUtils.showShort("当前夜间模式不可切换主题");
            } else {
                ARouter.getInstance().build(RouterActivityPath.Theme.PAGER_THEME).navigation();
            }
        });
    }

    @Override
    protected void initData() {
        ViewColorUtils.setToolbarBackColor(this, binding.toolbar, null);
        // 获取当前的主题
        if (!SPStaticUtils.getBoolean(Constants.switch_nightMode)) {
            int color = SPStaticUtils.getInt(Constants.color, R.color.colorPrimary);
            binding.viewTheme.setImageDrawable(new ColorDrawable(getResources().getColor(color)));
        } else {
            binding.viewTheme.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        }
    }
}