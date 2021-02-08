package com.dbz.system.know;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.system.R;
import com.dbz.system.databinding.FragmentKnowBinding;
import com.dbz.system.know.adapter.KnowFgAdapter;

@Route(path = RouterFragmentPath.System.PAGER_KNOW)
public class KnowFragment extends BaseFragment<FragmentKnowBinding, KnowledgeViewModel> implements ScrollToTop {

    private int cid;
    private int page;
    private KnowFgAdapter mAdapter;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know;
    }

    @Override
    protected KnowledgeViewModel getViewModel() {
        return new ViewModelProvider(this).get(KnowledgeViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        if (bundle != null) {
            cid = bundle.getInt("id");
        }
        setLoadSir(binding.refreshLayout);
        mAdapter = new KnowFgAdapter(R.layout.item_know);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            mViewModel.getTreeJsonCid(page, cid);
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mViewModel.getTreeJsonCid(page, cid);
        });
        mViewModel.mDataBean.observe(this, dataBean -> {
            if (dataBean.getCurPage() == 1) {
                if (dataBean.getDatas().size() > 0){
                    mAdapter.setList(dataBean.getDatas());
                    showContent();
                } else {
                    showEmpty();
                }
                binding.refreshLayout.finishRefresh(true);
            } else {
                mAdapter.addData(dataBean.getDatas());
                binding.refreshLayout.finishLoadMore(true);
                showContent();
            }
        });
    }

    @Override
    protected void initData() {
        page = 0;
        showLoading();
        mViewModel.getTreeJsonCid(page, cid);
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}