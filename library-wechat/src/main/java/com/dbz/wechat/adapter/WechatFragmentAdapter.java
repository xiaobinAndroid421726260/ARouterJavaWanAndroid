package com.dbz.wechat.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.network.retrofit.bean.wechat.WechatBean;
import com.dbz.wechat.WechatViewPagerFragment;

import java.util.ArrayList;
import java.util.List;


public class WechatFragmentAdapter extends FragmentPagerAdapter {

    private final List<WechatBean.DataBean> mDataBean = new ArrayList<>();
    private final List<Fragment> mFragments = new ArrayList<>();

    public WechatFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addData(List<WechatBean.DataBean> data) {
        if (null != data) {
            addData(data, true);
        }
    }

    public void addData(List<WechatBean.DataBean> data, boolean refresh) {
        if (refresh) {
            clear();
        }
        mDataBean.addAll(data);
        for (int i = 0; i < data.size(); i++) {
            WechatViewPagerFragment fragment = (WechatViewPagerFragment) ARouter.getInstance()
                    .build(RouterFragmentPath.Wechat.PAGER_INSIDE)
                    .withInt("id", data.get(i).getId())
                    .navigation();
            mFragments.add(fragment);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mDataBean != null) {
            mDataBean.clear();
            mFragments.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.size() > 0 ? mFragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return mDataBean.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDataBean.get(position).getName();
    }

}