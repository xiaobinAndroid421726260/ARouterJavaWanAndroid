package com.dbz.square;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.network.retrofit.bean.square.SquareBean;
import com.dbz.square.databinding.ItemSquareBinding;

import org.jetbrains.annotations.NotNull;

public class SquareAdapter extends BaseQuickAdapter<SquareBean.DataBean.DatasBean, BaseViewHolder> {

    public SquareAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SquareBean.DataBean.DatasBean datasBean) {
        BaseDataBindingHolder<ItemSquareBinding> bindingHolder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
        ItemSquareBinding binding = bindingHolder.getDataBinding();
        binding.setViewmodel(datasBean);
        binding.image.setVisibility(StringUtils.isEmpty(datasBean.getEnvelopePic()) ? View.GONE : View.VISIBLE);
        binding.ivCollection.setOnClickListener(v -> ToastUtils.showShort("收藏"));
        binding.executePendingBindings();
        baseViewHolder.itemView.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(RouterActivityPath.WebView.PAGER_WEB)
                    .withString("url", datasBean.getLink())
                    .withInt("id", datasBean.getId())
                    .withString("title", datasBean.getTitle())
                    .navigation();
        });
    }
}