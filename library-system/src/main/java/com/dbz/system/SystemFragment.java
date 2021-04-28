package com.dbz.system;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.ViewColorUtils;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.system.adapter.SystemAdapter;
import com.dbz.system.databinding.FragmentSystemBinding;
import com.dbz.system.viewmodel.SystemViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterFragmentPath.System.PAGER_SYSTEM)
public class SystemFragment extends BaseFragment<FragmentSystemBinding, SystemViewModel> implements ScrollToTop {

    private final List<String> mTitles = new ArrayList<>();
    private final List<Fragment> mFragment = new ArrayList<>();
    private SystemAdapter mAdapter;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_system;
    }

    @Override
    protected SystemViewModel getViewModel() {
        return null;
    }

    @Override
    protected void initView(Bundle bundle) {
        mTitles.add("体系");
        mTitles.add("导航");
        mFragment.add(SystemOneFragment.newInstance());
        mFragment.add(SystemTwoFragment.newInstance());
        mAdapter = new SystemAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTitles, mFragment);
        binding.viewPager.setAdapter(mAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        ViewColorUtils.setTaLayoutViewTextColor(binding.tabLayout);
    }

    @Override
    public void scrollToTop() {
        if (mAdapter.getCount() == 0) return;
        Fragment fragment = mAdapter.getItem(binding.viewPager.getCurrentItem());
        if (fragment instanceof ScrollToTop) {
            ((ScrollToTop) fragment).scrollToTop();
        }
    }
}
