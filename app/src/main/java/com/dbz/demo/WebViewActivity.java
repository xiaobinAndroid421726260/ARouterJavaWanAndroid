package com.dbz.demo;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseActivity;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.demo.databinding.ActivityWebViewBinding;

@Route(path = RouterActivityPath.WebView.PAGER_WEB)
public class WebViewActivity extends BaseActivity<ActivityWebViewBinding, BaseViewModel> {

    private String url, title;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
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
        if (getIntent() != null) {
            url = getIntent().getExtras().getString("url");
            id = getIntent().getExtras().getInt("id");
            title = getIntent().getExtras().getString("title");
        }
        setSupportActionBar(binding.toolbar);
        binding.tvTitle.setText("正在加载中...");
        binding.tvTitle.setVisibility(View.VISIBLE);
        binding.toolbar.setNavigationIcon(R.drawable.onback_white);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.x5webview.loadUrl(url);
        binding.tvTitle.setText(title);
    }

    @Override
    protected void initData() {

    }
}