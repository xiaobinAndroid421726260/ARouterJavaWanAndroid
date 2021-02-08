package com.dbz.system.adapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.network.retrofit.bean.system.SystemBean;
import com.dbz.system.databinding.ItemOneBinding;
import com.dbz.system.know.KnowledgeActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class SystemOneAdapter extends BaseQuickAdapter<SystemBean.DataBean, BaseViewHolder> {

    public SystemOneAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SystemBean.DataBean dataBean) {
        BaseDataBindingHolder<ItemOneBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
        ItemOneBinding binding = holder.getDataBinding();
        binding.setViewmodel(dataBean);
        binding.executePendingBindings();
        if (dataBean.getChildren().size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < dataBean.getChildren().size(); i++) {
                builder.append(dataBean.getChildren().get(i).getName()).append("   ");
            }
            binding.titleSecond.setText(builder.toString());
        }
        baseViewHolder.itemView.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(RouterActivityPath.Know.PAGER_KNOW)
                        .withString("name", dataBean.getName())
                        .withSerializable("data", (Serializable) dataBean.getChildren())
                        .navigation());
    }
}