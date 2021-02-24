package com.dbz.project;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.project.adapter.ProjectAdapter;
import com.dbz.project.databinding.FragmentListBinding;
import com.dbz.project.viewmodel.ProjectListViewModel;

@Route(path = RouterFragmentPath.Project.PAGER_INSIDE)
public class ProjectListFragment extends BaseFragment<FragmentListBinding, ProjectListViewModel> implements ScrollToTop {

    private BaseLoadMoreModule loadMoreModule;
    private ProjectAdapter mAdapter;
    private int page = 1;
    private int cid = 0;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected ProjectListViewModel getViewModel() {
        return new ViewModelProvider(this).get(ProjectListViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        if (bundle != null){
            cid = bundle.getInt("id");
        }
        setLoadSir(binding.refreshLayout);
        mAdapter = new ProjectAdapter(R.layout.item_project);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadMoreModule = mAdapter.getLoadMoreModule();
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        loadMoreModule.setOnLoadMoreListener(() -> {
            page++;
            mViewModel.getProjectCidJson(page, cid);
        });
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mViewModel.getProjectCidJson(page, cid);
        });
        binding.refreshLayout.setEnableLoadMore(false);
        mViewModel.mDataBean.observe(this, dataBean -> {
            if (dataBean.getCurPage() == 1){
                if (dataBean.getDatas().size() > 0){
                    mAdapter.setList(dataBean.getDatas());
                    showContent();
                } else {
                    showEmpty();
                }
                binding.refreshLayout.finishRefresh(true);
            } else {
                if (dataBean.getDatas().size() > 0){
                    mAdapter.addData(dataBean.getDatas());
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
        mViewModel.getProjectCidJson(page, cid);
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}