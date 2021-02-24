package com.dbz.home;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.home.databinding.ItemHomeBinding;
import com.dbz.network.retrofit.bean.home.TopBean;

import org.jetbrains.annotations.NotNull;

public class HomeAdapter extends BaseQuickAdapter<TopBean.DataBean, BaseViewHolder> implements LoadMoreModule {

    public HomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TopBean.DataBean dataBean) {
        BaseDataBindingHolder<ItemHomeBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
        ItemHomeBinding binding = holder.getDataBinding();
        binding.setViewmodel(dataBean);
        binding.image.setVisibility(StringUtils.isEmpty(dataBean.getEnvelopePic()) ? View.GONE : View.VISIBLE);
        binding.ivCollection.setOnClickListener(v -> ToastUtils.showShort("收藏"));
        binding.executePendingBindings();
        baseViewHolder.itemView.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(RouterActivityPath.WebView.PAGER_WEB)
                    .withString("url", dataBean.getLink())
                    .withInt("id", dataBean.getId())
                    .withString("title", dataBean.getTitle())
                    .navigation();
        });
    }
}