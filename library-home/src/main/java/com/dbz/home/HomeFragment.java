package com.dbz.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.home.databinding.FragmentHomeBinding;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements ScrollToTop {

    private HomeAdapter mAdapter;
    private Banner<String, BannerImageAdapter> mBanner;
    private int page = 0;

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        setLoadSir(binding.refreshLayout);
        mAdapter = new HomeAdapter(R.layout.item_home);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View view = getHeaderView();
        mBanner = view.findViewById(R.id.banner);
        mBanner.setIndicator(new CircleIndicator(getActivity()));
        mAdapter.addHeaderView(view);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            mViewModel.getBannerJson();
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mViewModel.getTopJson(page, false);
        });
        mViewModel.mBannerUrl.observe(this, strings -> {
            if (mBanner != null) {
                mBanner.setAdapter(new BannerImageAdapter(strings));
            }
            page = 0;
            mViewModel.getTopJson(page, true);
        });
        mViewModel.mHomeBean.observe(this, homeBean -> {
            if (homeBean.isRefresh()){
                mAdapter.setList(homeBean.getData());
                showContent();
                if (homeBean.getData().size() == 0){
                    showEmpty();
                }
                binding.refreshLayout.finishRefresh(true);
            } else {
                binding.refreshLayout.finishLoadMore(true);
                if (homeBean.getData().size() > 0){
                    mAdapter.addData(homeBean.getData());
                    showContent();
                }
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        mViewModel.getBannerJson();
    }

    private View getHeaderView() {
        return LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_header_home, binding.recyclerView, false);
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}