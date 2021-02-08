package com.dbz.square;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.square.databinding.FragmentSquareBinding;

@Route(path = RouterFragmentPath.Square.PAGER_SQUARE)
public class SquareFragment extends BaseFragment<FragmentSquareBinding, SquareViewModel> implements ScrollToTop {

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
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            mViewModel.getSquareModel(page, true);
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mViewModel.getSquareModel(page, false);
        });
        mViewModel.mErrorMsg.observe(this, this::showFailure);
        mViewModel.mDataBean.observe(this, dataBeans -> {
            if (dataBeans.isRefresh()){
                if (dataBeans.getData().getDatas().size() == 0){
                    showEmpty();
                }
                binding.refreshLayout.finishRefresh(true);
            } else {
                binding.refreshLayout.finishLoadMore(true);
            }
            if (dataBeans.getData().getDatas().size() > 0){
                mAdapter.setList(dataBeans.getData().getDatas());
                showContent();
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        mViewModel.getSquareModel(page, true);
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}