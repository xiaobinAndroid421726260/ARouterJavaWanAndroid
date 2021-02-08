package com.dbz.wechat.adapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.network.retrofit.bean.wechat.WechatFragmentBean;
import com.dbz.wechat.databinding.ItemFragmentPagerBinding;

import org.jetbrains.annotations.NotNull;

public class WechatAdapter extends BaseQuickAdapter<WechatFragmentBean.DataBean.DatasBean, BaseViewHolder> {

    public WechatAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WechatFragmentBean.DataBean.DatasBean dataBean) {
        BaseDataBindingHolder<ItemFragmentPagerBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
        ItemFragmentPagerBinding binding = holder.getDataBinding();
        binding.setViewmodel(dataBean);
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