package com.dbz.system.know;

import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseActivity;
import com.dbz.base.ScrollToTop;
import com.dbz.base.ViewColorUtils;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.network.retrofit.bean.system.SystemBean;
import com.dbz.system.R;
import com.dbz.system.databinding.ActivityKnowledgeBinding;
import com.dbz.system.know.adapter.KnowAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterActivityPath.Know.PAGER_KNOW)
public class KnowledgeActivity extends BaseActivity<ActivityKnowledgeBinding, BaseViewModel> {

    private String name;
    private List<SystemBean.DataBean.ChildrenBean> mDataBean;
    private final List<String> mTitle = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge;
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
            name = getIntent().getExtras().getString("name");
            mDataBean = (List<SystemBean.DataBean.ChildrenBean>) getIntent().getExtras().getSerializable("data");
        }
        mTitle.clear();
        if (mDataBean != null && mDataBean.size() > 0) {
            for (int i = 0; i < mDataBean.size(); i++) {
                mTitle.add(mDataBean.get(i).getName());
            }
        }
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(name);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        KnowAdapter mAdapter = new KnowAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPage.setAdapter(mAdapter);
        binding.viewPage.setOffscreenPageLimit(mDataBean.size());
        mAdapter.addData(mDataBean, mTitle);
        binding.tabLayout.setupWithViewPager(binding.viewPage);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPage.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.actionButton.setOnClickListener(v -> {
            Fragment fragment = mAdapter.getItem(binding.viewPage.getCurrentItem());
            if (fragment instanceof ScrollToTop) {
                ((ScrollToTop) fragment).scrollToTop();
            }
        });
    }

    @Override
    protected void initData() {
        ViewColorUtils.setToolbarBackColor(this, binding.toolbar, binding.actionButton);
        ViewColorUtils.setTaLayoutViewTextColor(binding.tabLayout);
    }
}