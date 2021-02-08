package com.dbz.demo.view;

import android.app.Activity;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.NetworkUtils;
import com.dbz.demo.R;
import com.dbz.demo.utils.DensityUtils;

import me.jessyan.autosize.AutoSize;

/**
 * description:
 *
 * @author Db_z
 * date 2020/3/24 10:26
 * @version V1.0
 */
public class X5WebView extends WebView {

    private ProgressBar mProgressBar;
    private WebSettings webSettings;

    public X5WebView(Context context) {
        this(context, null);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initWebViewSettings(context);
        this.setWebViewClient(mWebViewClient);
        this.setWebChromeClient(mWebChromeClient);
//        this.getView().setClickable(true);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            sslErrorHandler.proceed();
        }
    };

    public WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int progress) {
            super.onProgressChanged(webView, progress);
            if (progress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE) {
                    mProgressBar.setVisibility(VISIBLE);
                }
                mProgressBar.setProgress(progress);
            }
        }
    };

    private void initWebViewSettings(Context context) {
        mProgressBar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 3));
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_back));
        mProgressBar.setProgress(0);
        addView(mProgressBar);
        webSettings = this.getSettings();
        //如果访问的页面中要与Javascript交互，则WebView必须设置支持Javascript。默认为false
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //允许JS自动打开新窗口，默认为false
        //支持屏幕缩放。默认为true，若设置setBuiltInZoomControls(true)，则必须开启此功能
        webSettings.setSupportZoom(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。默认为false，表示WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件，默认为true
        webSettings.setSupportMultipleWindows(false); //禁止在新窗口中打开目标网页，默认为false
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到合适的大小
        webSettings.setGeolocationEnabled(true);
        webSettings.setLoadWithOverviewMode(true); //缩放至屏幕的大小，默认为false
        webSettings.setAppCacheEnabled(true); //开启APP缓存功能，默认为false
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        String appCachePath = context.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCachePath); //开启APP缓存功能必须设置缓存路径
        if (NetworkUtils.isConnected()) {
            //根据cache-control决定是否从网络上取数据。
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //无论是否有网络，无论是否过期，或者no-cache，只要本地有缓存，都使用缓存
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setDatabaseEnabled(true); //开启数据库形式存储，默认为false
        webSettings.setDomStorageEnabled(true); //开启DOM形式存储，默认为false
        //解决在Android 5.0上 WebView 默认不允许加载http和https混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }

    @Override
    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
        //重写该方法实现density适配
        if (getContext() != null && getContext() instanceof Activity) {
            AutoSize.autoConvertDensityOfGlobal((Activity) getContext());
        }
    }
}