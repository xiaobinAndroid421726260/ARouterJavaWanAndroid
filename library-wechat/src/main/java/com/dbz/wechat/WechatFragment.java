package com.dbz.wechat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.wechat.adapter.WechatFragmentAdapter;
import com.dbz.wechat.databinding.FragmentWechatBinding;
import com.dbz.wechat.viewmodel.WechatViewModel;
import com.google.android.material.tabs.TabLayout;

@Route(path = RouterFragmentPath.Wechat.PAGER_WECHER)
public class WechatFragment extends BaseFragment<FragmentWechatBinding, WechatViewModel> implements ScrollToTop {

    private WechatFragmentAdapter mAdapter;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat;
    }

    @Override
    protected WechatViewModel getViewModel() {
        return new ViewModelProvider(this).get(WechatViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        mAdapter = new WechatFragmentAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
            if (dataBeans.size() > 0){
                binding.viewPager.setOffscreenPageLimit(dataBeans.size());
                mAdapter.addData(dataBeans);
            }
        });
    }

    @Override
    protected void initData() {
        mViewModel.getWechatArticle();
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