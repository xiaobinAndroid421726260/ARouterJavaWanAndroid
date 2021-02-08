package com.dbz.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.network.retrofit.utils.LogUtils;
import com.dbz.project.adapter.ProjectFragmentAdapter;
import com.dbz.project.databinding.FragmentProjectBinding;
import com.dbz.project.viewmodel.ProjectViewModel;
import com.google.android.material.tabs.TabLayout;

@Route(path = RouterFragmentPath.Project.PAGER_PROJECT)
public class ProjectFragment extends BaseFragment<FragmentProjectBinding, ProjectViewModel> implements ScrollToTop {

    private ProjectFragmentAdapter mAdapter;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected ProjectViewModel getViewModel() {
        return new ViewModelProvider(this).get(ProjectViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        mAdapter = new ProjectFragmentAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
        mViewModel.mDataBean.observe(this, dataBeans -> {
            binding.viewPager.setOffscreenPageLimit(dataBeans.size());
            mAdapter.addData(dataBeans);
        });
    }

    @Override
    protected void initData() {
        mViewModel.getProjectTreeJson();
    }

    @Override
    public void scrollToTop() {
        LogUtils.e("------ffffff--scrollToTop()");
        if (mAdapter.getCount() == 0) return;
        Fragment fragment = mAdapter.getItem(binding.viewPager.getCurrentItem());
        LogUtils.e("------ffffff--fragment = " + fragment);
        if (fragment instanceof ScrollToTop) {
            LogUtils.e("------ffffff--fragment instanceof ScrollToTop");
            ((ScrollToTop) fragment).scrollToTop();
        }
    }
}