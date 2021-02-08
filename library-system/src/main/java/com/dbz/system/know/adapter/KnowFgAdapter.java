package com.dbz.system.know.adapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.network.retrofit.bean.system.SystemJsonBean;
import com.dbz.system.databinding.ItemKnowBinding;

import org.jetbrains.annotations.NotNull;

public class KnowFgAdapter extends BaseQuickAdapter<SystemJsonBean.DataBean.DatasBean, BaseViewHolder> {

    public KnowFgAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SystemJsonBean.DataBean.DatasBean datasBean) {
        BaseDataBindingHolder<ItemKnowBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
        ItemKnowBinding binding = holder.getDataBinding();
        binding.setViewmodel(datasBean);
        binding.executePendingBindings();
        baseViewHolder.itemView.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(RouterActivityPath.WebView.PAGER_WEB)
                        .withString("url", datasBean.getLink())
                        .withString("title", datasBean.getTitle())
                        .withInt("id", datasBean.getId())
                        .navigation()
        );
    }
}