package com.dbz.system;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.BaseFragment;
import com.dbz.base.ScrollToTop;
import com.dbz.network.retrofit.bean.system.NaviBean;
import com.dbz.system.databinding.FragmentTwoBinding;
import com.dbz.system.databinding.ItemTwoTitleBinding;
import com.dbz.system.navigation.SystemTwoAdapter;
import com.dbz.system.viewmodel.SystemTwoViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SystemTwoFragment extends BaseFragment<FragmentTwoBinding, SystemTwoViewModel> implements ScrollToTop {

    private BaseQuickAdapter<NaviBean.DataBean, BaseViewHolder> mAdapter;
    private SystemTwoAdapter mTwoAdapter;
    private final List<NaviBean.DataBean.ArticlesBean> mArticlesBeans = new ArrayList<>();

    public static SystemTwoFragment newInstance() {
        return new SystemTwoFragment();
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected SystemTwoViewModel getViewModel() {
        return new ViewModelProvider(this).get(SystemTwoViewModel.class);
    }

    @Override
    protected void initView(Bundle bundle) {
        setLoadSir(binding.rootview);
        binding.recyclerViewTitle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BaseQuickAdapter<NaviBean.DataBean, BaseViewHolder>(R.layout.item_two_title) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, NaviBean.DataBean dataBean) {
                BaseDataBindingHolder<ItemTwoTitleBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
                ItemTwoTitleBinding itemBinding = holder.getDataBinding();
                itemBinding.setViewmodel(dataBean);
                itemBinding.executePendingBindings();
            }
        };
        binding.recyclerViewTitle.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTwoAdapter = new SystemTwoAdapter(R.layout.item_two_content_title);
        binding.recyclerView.setAdapter(mTwoAdapter);
        mTwoAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        mViewModel.mDataBean.observe(this, dataBeans -> {
            mArticlesBeans.clear();
            Observable.fromIterable(dataBeans).subscribe(new Observer<NaviBean.DataBean>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull NaviBean.DataBean dataBean) {
                    dataBean.setCheck(false);
                    mArticlesBeans.addAll(dataBean.getArticles());
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    mViewModel.mErrorMsg.setValue(e.getMessage());
                }

                @Override
                public void onComplete() {
                    dataBeans.get(0).setCheck(true);
                    mAdapter.setList(dataBeans);
                    mTwoAdapter.setList(dataBeans);
                    showContent();
                }
            });
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Observable.fromIterable(mAdapter.getData()).subscribe(new Observer<NaviBean.DataBean>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull NaviBean.DataBean dataBean) {
                    dataBean.setCheck(false);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                    mAdapter.getData().get(position).setCheck(true);
                    mAdapter.notifyDataSetChanged();
                    LinearLayoutManager layoutManager = (LinearLayoutManager) binding.recyclerView.getLayoutManager();
                    layoutManager.scrollToPositionWithOffset(position, 0);
                }
            });
        });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@androidx.annotation.NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取滚动时的第一条展示的position
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int position = 0;
                for (int i = 0; i < firstVisibleItemPosition; i++) {
                    position += mAdapter.getData().get(i).getArticles().size();
                }
                //获取右侧数据的关联id
                NaviBean.DataBean.ArticlesBean articlesBean = mArticlesBeans.get(position);
                int outId = articlesBean.getChapterId();
                //记录外部id， 更新左侧状态栏状态
                int pos = 0;
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    mAdapter.getData().get(i).setCheck(false);
                    int id = mAdapter.getData().get(i).getCid();
                    if ((outId == id)) {
                        pos = i;
                        mAdapter.getData().get(i).setCheck(true);
                    }
                }
                mAdapter.notifyDataSetChanged();
                binding.recyclerViewTitle.smoothScrollToPosition(pos);
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        mViewModel.getNaviJson();
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }
}