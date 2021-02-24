package com.dbz.system;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.system.adapter.SystemOneAdapter;
import com.dbz.system.databinding.FragmentOneBinding;
import com.dbz.system.viewmodel.SystemOneViewModel;

public class SystemOneFragment extends BaseFragment<FragmentOneBinding, SystemOneViewModel> implements ScrollToTop {

    private SystemOneAdapter mAdapter;

    public static SystemOneFragment newInstance() {
        return new SystemOneFragment();
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected SystemOneViewModel getViewModel() {
        return new ViewModelProvider(this).get(SystemOneViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        setLoadSir(binding.refreshLayout);
        mAdapter = new SystemOneAdapter(R.layout.item_one);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> mViewModel.getTreeJson());
        mViewModel.mDataBean.observe(this, dataBeans -> {
            mAdapter.setList(dataBeans);
            binding.refreshLayout.finishRefresh(true);
            showContent();
        });
    }

    @Override
    protected void initData() {
        showLoading();
        mViewModel.getTreeJson();
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}