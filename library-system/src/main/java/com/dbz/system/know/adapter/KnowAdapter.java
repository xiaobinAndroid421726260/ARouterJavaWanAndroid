package com.dbz.system.know.adapter;

import android.text.Html;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.network.retrofit.bean.system.SystemBean;

import java.util.ArrayList;
import java.util.List;

public class KnowAdapter extends FragmentStatePagerAdapter {

    private final List<String> mTitle = new ArrayList<>();
    private final List<Fragment> mFragments = new ArrayList<>();

    public KnowAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addData(List<SystemBean.DataBean.ChildrenBean> data, List<String> titles){
        for (int i = 0; i < data.size(); i++) {
            Fragment fragment = (Fragment) ARouter.getInstance()
                    .build(RouterFragmentPath.System.PAGER_KNOW)
                    .withInt("id", data.get(i).getId())
                    .navigation();
            mFragments.add(fragment);
        }
        mTitle.addAll(titles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Html.fromHtml(mTitle.get(position));
    }
}