package com.dbz.wechat;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.wechat.adapter.WechatAdapter;
import com.dbz.wechat.databinding.ViewpagerFragmentBinding;
import com.dbz.wechat.viewmodel.WechatViewPagerViewModel;

@Route(path = RouterFragmentPath.Wechat.PAGER_INSIDE)
public class WechatViewPagerFragment extends BaseFragment<ViewpagerFragmentBinding, WechatViewPagerViewModel> implements ScrollToTop {

    private BaseLoadMoreModule loadMoreModule;
    private WechatAdapter mAdapter;
    private int id, page = 1;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.viewpager_fragment;
    }

    @Override
    protected WechatViewPagerViewModel getViewModel() {
        return new ViewModelProvider(this).get(WechatViewPagerViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
        }
        setLoadSir(binding.refreshLayout);
        mAdapter = new WechatAdapter(R.layout.item_fragment_pager);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(() -> {
            page++;
            mViewModel.getUserArticleJson(id, page);
        });
        mAdapter.setAnimationFirstOnly(true);
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mViewModel.getUserArticleJson(id, page);
        });
        binding.refreshLayout.setEnableLoadMore(false);
        mViewModel.mDataBean.observe(this, datasBeans -> {
            if (datasBeans.getCurPage() == 1){
                if (datasBeans.getDatas().size() == 0){
                    showEmpty();
                }
                if (datasBeans.getDatas().size() > 0){
                    mAdapter.setList(datasBeans.getDatas());
                }
                showContent();
                binding.refreshLayout.finishRefresh(true);
            } else {
                if (datasBeans.getDatas().size() > 0){
                    mAdapter.addData(datasBeans.getDatas());
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
        page = 1;
        if (id != 0){
            mViewModel.getUserArticleJson(id, page);
        } else {
            if (mViewModel.mErrorMsg.getValue() != null){
                showFailure(mViewModel.mErrorMsg.getValue());
            }
        }
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}