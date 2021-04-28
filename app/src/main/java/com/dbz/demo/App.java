package com.dbz.demo;

import android.app.Application;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPStaticUtils;
import com.dbz.base.config.Constants;
import com.dbz.base.loadsir.EmptyCallback;
import com.dbz.base.loadsir.ErrorCallback;
import com.dbz.base.loadsir.LoadingCallback;
import com.dbz.base.loadsir.TimeoutCallback;
import com.dbz.network.retrofit.utils.LogUtils;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class App extends Application {


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            if (!SPStaticUtils.getBoolean(Constants.switch_nightMode)){
                int color = SPStaticUtils.getInt(Constants.color, R.color.colorPrimary);
                if (color == R.color.accent_white){
                    layout.setPrimaryColorsId(color, R.color.black);
                } else {
                    layout.setPrimaryColorsId(color, R.color.accent_white);
                }
            } else {
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white_color);
            }
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
        // 获取当前的主题
        if (SPStaticUtils.getBoolean(Constants.switch_nightMode)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}