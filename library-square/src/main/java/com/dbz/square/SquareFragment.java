package com.dbz.square;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.square.databinding.FragmentSquareBinding;

@Route(path = RouterFragmentPath.Square.PAGER_SQUARE)
public class SquareFragment extends BaseFragment<FragmentSquareBinding, SquareViewModel> implements ScrollToTop {

    private BaseLoadMoreModule loadMoreModule;
    private SquareAdapter mAdapter;
    private int page = 0;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    protected SquareViewModel getViewModel() {
        return new ViewModelProvider(this).get(SquareViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        setLoadSir(binding.refreshLayout);
        mAdapter = new SquareAdapter(R.layout.item_square);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadMoreModule = mAdapter.getLoadMoreModule();
        mAdapter.setAnimationFirstOnly(true);
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        loadMoreModule.setOnLoadMoreListener(() -> {
            page++;
            mViewModel.getSquareModel(page);
        });
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            mViewModel.getSquareModel(page);
        });
        binding.refreshLayout.setEnableLoadMore(false);
        mViewModel.mDataBeans.observe(this, dataBeans -> {
            if (dataBeans.getCurPage() == 1){
                if (dataBeans.getDatas().size() == 0) {
                    showEmpty();
                }
                if (dataBeans.getDatas().size() > 0){
                    mAdapter.setList(dataBeans.getDatas());
                    showContent();
                }
                binding.refreshLayout.finishRefresh(true);
            } else {
                if (dataBeans.getDatas().size() > 0){
                    mAdapter.addData(dataBeans.getDatas());
                    showContent();
                    loadMoreModule.loadMoreComplete();
                } else {
                    loadMoreModule.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        mViewModel.getSquareModel(page);
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}