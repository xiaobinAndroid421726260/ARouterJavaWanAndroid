package com.dbz.base;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.SPStaticUtils;
import com.dbz.base.config.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;

public class ViewColorUtils {

    /**
     * 设置主题 Toolbar 背景颜色
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void setToolbarBackColor(AppCompatActivity activity, Toolbar toolbar, FloatingActionButton actionButton) {
        boolean nightMode = getNightMode();
        int color = getThemeColor();
        if (nightMode) {
            setToolbarWhiteExceptColor(activity, toolbar, color);
        } else {
            if (activity.getSupportActionBar() != null) {
                if (color == R.color.accent_white) {
                    setToolbarWhiteColor(activity, toolbar, color);
                } else {
                    setToolbarWhiteExceptColor(activity, toolbar, color);
                }
                activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(color)));
            }
        }
        if (actionButton != null) {
            actionButton.setImageDrawable(color == R.color.accent_white ? activity
                    .getResources()
                    .getDrawable(R.drawable.ic_arrow_upward_black_24dp) : activity
                    .getResources()
                    .getDrawable(R.drawable.ic_arrow_upward_white_24dp));
            actionButton.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(color)));
        }
    }

    /**
     * 设置TaLayout背景颜色及文本颜色
     */
    public static void setTaLayoutViewTextColor(TabLayout tabLayout) {
        boolean nightMode = getNightMode();
        int color = getThemeColor();
        if (nightMode) {
            tabLayout.setBackgroundColor(tabLayout.getContext().getResources().getColor(R.color.colorPrimary));
            tabLayout.setTabTextColors(tabLayout.getContext().getResources().getColor(R.color.arrow_color),
                    tabLayout.getContext().getResources().getColor(R.color.color_theme_text));
            tabLayout.setSelectedTabIndicatorColor(tabLayout.getContext().getResources().getColor(R.color.color_theme_text));
        } else {
            if (color == R.color.accent_white) {
                tabLayout.setTabTextColors(tabLayout.getContext().getResources().getColor(R.color.Grey700),
                        tabLayout.getContext().getResources().getColor(R.color.dark_dark));
                tabLayout.setSelectedTabIndicatorColor(tabLayout.getContext().getResources().getColor(R.color.dark_dark));
            } else {
                tabLayout.setTabTextColors(tabLayout.getContext().getResources().getColor(R.color.Grey300),
                        tabLayout.getContext().getResources().getColor(R.color.accent_white));
                tabLayout.setSelectedTabIndicatorColor(tabLayout.getContext().getResources().getColor(R.color.accent_white));
            }
        }
    }

    public static boolean getNightMode() {
        return SPStaticUtils.getBoolean(Constants.switch_nightMode, false);
    }

    public static int getThemeColor() {
        int color;
        if (!SPStaticUtils.getBoolean(Constants.switch_nightMode, false)) {
            color = SPStaticUtils.getInt(Constants.color, R.color.colorPrimary);
        } else {
            color = R.color.colorPrimary;
        }
        return color;
    }

    /**
     * 设置白色主题
     */
    private static void setToolbarWhiteColor(AppCompatActivity activity, Toolbar toolbar, int color) {
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_black);
        ImmersionBar.with(activity)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(color)
                .init();
    }

    /**
     * 设置除白色以外的主题
     */
    private static void setToolbarWhiteExceptColor(AppCompatActivity activity, Toolbar toolbar, int color) {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_white);
        ImmersionBar.with(activity)
                .statusBarDarkFont(false)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(color)
                .init();
    }
}