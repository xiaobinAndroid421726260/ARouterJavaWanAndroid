package com.dbz.wechat;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.network.retrofit.utils.LogUtils;
import com.dbz.wechat.adapter.WechatAdapter;
import com.dbz.wechat.databinding.ViewpagerFragmentBinding;
import com.dbz.wechat.viewmodel.WechatViewPagerViewModel;

@Route(path = RouterFragmentPath.Wechat.PAGER_INSIDE)
public class WechatViewPagerFragment extends BaseFragment<ViewpagerFragmentBinding, WechatViewPagerViewModel> implements ScrollToTop {

    private WechatAdapter mAdapter;
    private int id, page;

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
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            mViewModel.getUserArticleJson(id, page, true);
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mViewModel.getUserArticleJson(id, page, false);
        });
        mViewModel.mDataBean.observe(this, datasBeans -> {
            if (datasBeans.getData().getCurPage() == 0){
                if (datasBeans.getData().getDatas().size() == 0){
                    showEmpty();
                }
                if (datasBeans.getData().getDatas().size() > 0){
                    mAdapter.setList(datasBeans.getData().getDatas());
                }
                binding.refreshLayout.finishRefresh(true);
            } else {
                mAdapter.addData(datasBeans.getData().getDatas());
                binding.refreshLayout.finishLoadMore(true);
            }
            showContent();
        });
    }

    @Override
    protected void initData() {
        showLoading();
        page = 0;
        if (id != 0){
            mViewModel.getUserArticleJson(id, page, true);
        } else {
            if (mViewModel.mErrorMsg.getValue() != null){
                showFailure(mViewModel.mErrorMsg.getValue());
            }
        }
    }

    @Override
    public void scrollToTop() {
        LogUtils.e("--------wwwwwwwww scrollToTop");
        binding.recyclerView.smoothScrollToPosition(0);
    }
}