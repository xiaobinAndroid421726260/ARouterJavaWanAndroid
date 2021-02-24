package com.dbz.project.adapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.network.retrofit.bean.project.ProjectBean;
import com.dbz.project.databinding.ItemProjectBinding;

import org.jetbrains.annotations.NotNull;

public class ProjectAdapter extends BaseQuickAdapter<ProjectBean.DataBean.DatasBean, BaseViewHolder> implements LoadMoreModule {

    public ProjectAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectBean.DataBean.DatasBean dataBean) {
        BaseDataBindingHolder<ItemProjectBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
        ItemProjectBinding binding = holder.getDataBinding();
        binding.setViewmodel(dataBean);
        binding.executePendingBindings();
        binding.ivCollection.setOnClickListener(v -> ToastUtils.showShort("收藏"));
        baseViewHolder.itemView.setOnClickListener(v ->
            ARouter.getInstance()
                    .build(RouterActivityPath.WebView.PAGER_WEB)
                    .withString("url", dataBean.getLink())
                    .withInt("id", dataBean.getId())
                    .withString("title", dataBean.getTitle())
                    .navigation()
        );
    }
}