package com.dbz.system.navigation;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.network.retrofit.bean.system.NaviBean;
import com.dbz.system.databinding.ItemTwoContentBinding;
import com.dbz.system.databinding.ItemTwoContentTitleBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import org.jetbrains.annotations.NotNull;


public class SystemTwoAdapter extends BaseQuickAdapter<NaviBean.DataBean, BaseViewHolder> {

    public SystemTwoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NaviBean.DataBean dataBean) {
        BaseDataBindingHolder<ItemTwoContentTitleBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
        ItemTwoContentTitleBinding binding = holder.getDataBinding();
        binding.setViewmodel(dataBean);
        binding.executePendingBindings();
        TagAdapter<NaviBean.DataBean.ArticlesBean> adapter = new TagAdapter<NaviBean.DataBean.ArticlesBean>(dataBean.getArticles()) {
            @Override
            public View getView(FlowLayout parent, int position, NaviBean.DataBean.ArticlesBean articlesBean) {
                ItemTwoContentBinding childrenBinding = ItemTwoContentBinding.inflate(LayoutInflater.from(getContext()));
                childrenBinding.setViewmodel(articlesBean);
                childrenBinding.executePendingBindings();
                return childrenBinding.getRoot();
            }
        };
        binding.flowlayout.setAdapter(adapter);
        binding.flowlayout.setOnTagClickListener((view, position, parent) -> {
            ARouter.getInstance()
                    .build(RouterActivityPath.WebView.PAGER_WEB)
                    .withInt("id", dataBean.getArticles().get(position).getId())
                    .withString("url", dataBean.getArticles().get(position).getLink())
                    .withString("title", dataBean.getArticles().get(position).getTitle())
                    .navigation();
            return false;
        });
    }
}