package com.dbz.demo;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SPStaticUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dbz.base.BaseActivity;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.base.view.RippleAnimation;
import com.dbz.base.viewmodel.BaseViewModel;
import com.dbz.demo.bean.ThemeBean;
import com.dbz.demo.databinding.ActivityThemeBinding;
import com.dbz.demo.databinding.ItemThemeBinding;
import com.gyf.immersionbar.ImmersionBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterActivityPath.Theme.PAGER_THEME)
public class ThemeActivity extends BaseActivity<ActivityThemeBinding, BaseViewModel> {

    private ThemeAdapter mAdapter;
    private final List<ThemeBean> mThemes = new ArrayList<>();
    private int position = 0;
    private ThemeBean mDataBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_theme;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("主题风格");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_black);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        mAdapter = new ThemeAdapter(R.layout.item_theme);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) binding.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (this.position != position){
//                RippleAnimation.create().setDuration(1000).start();
                mDataBean = mAdapter.getData().get(position);
                SPStaticUtils.put("theme", mDataBean.getTheme());
                mDataBean.setChoose(true);
                mAdapter.setData(position, mDataBean);
                setStatusBarColor(mDataBean.getColor());
//                if (mDataBean.getColor() == R.color.accent_white) {
//                    themeNv.ivBackNavigationBar.imageTintList =
//                            ColorStateList.valueOf(color(R.color.color_on_theme_text))
//                    themeNv.tvLeftTitleNavigationBar.textColor = color(R.color.color_on_theme_text)
//                } else {
//                    themeNv.ivBackNavigationBar.imageTintList =
//                            ColorStateList.valueOf(color(R.color.color_theme_text))
//                    themeNv.tvLeftTitleNavigationBar.textColor = color(R.color.color_theme_text)
//                }
            }
        });
    }

    @Override
    protected void initData() {
        mThemes.clear();
        mThemes.add(new ThemeBean(R.color.accent_white, R.style.AppTheme_White, "白色", false));
        mThemes.add(new ThemeBean(R.color.accent_red, R.style.AppTheme_Red, "红色", false));
        mThemes.add(new ThemeBean(R.color.accent_pink, R.style.AppTheme_Pink, "粉色", false));
        mThemes.add(new ThemeBean(R.color.accent_orange, R.style.AppTheme_Orange, "橙色", false));
        mThemes.add(new ThemeBean(R.color.accent_pale_blue, R.style.AppTheme_PaleBlue, "蓝色", false));
        mThemes.add(new ThemeBean(R.color.accent_green, R.style.AppTheme_Gree, "绿色", false));
        mThemes.add(new ThemeBean(R.color.accent_cyan, R.style.AppTheme_Cyan, "青色", false));
        mThemes.add(new ThemeBean(R.color.accent_indigo, R.style.AppTheme_Indigo, "蓝色", false));
        mThemes.add(new ThemeBean(R.color.accent_purple, R.style.AppTheme_Purple, "紫色", false));
        mThemes.add(new ThemeBean(R.color.accent_brown, R.style.AppTheme_Brown, "棕色", false));
        for (int i = 0; i < mThemes.size(); i++) {
            if (SPStaticUtils.getInt("theme", -1) == mThemes.get(i).getTheme()){
                mThemes.get(i).setChoose(true);
                position = i;
            }
        }
        mAdapter.setList(mThemes);
    }

    @Override
    protected void initStatusColor() {
        super.initStatusColor();

    }

    static class ThemeAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> {

        public ThemeAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, ThemeBean themeBean) {
            BaseDataBindingHolder<ItemThemeBinding> holder = new BaseDataBindingHolder<>(baseViewHolder.itemView);
            ItemThemeBinding binding = (ItemThemeBinding) holder.getDataBinding();
            if (themeBean.getColor() == R.color.accent_white) {
                binding.flThemeColor.getDelegate()
                        .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.text_color_primary_alpha_50));
                binding.tvThemeColor.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_primary_alpha_50));
            } else {
                binding.flThemeColor.getDelegate()
                        .setBackgroundColor(ContextCompat.getColor(getContext(), themeBean.getColor()));
                binding.tvThemeColor.setTextColor(ContextCompat.getColor(getContext(), themeBean.getColor()));
                binding.tvUserTheme.getDelegate().setStrokeColor(ContextCompat.getColor(getContext(),
                        !themeBean.isChoose() ? R.color.text_color_primary_alpha_50 : themeBean.getColor()));
                binding.tvUserTheme.setTextColor(ContextCompat.getColor(getContext(),
                        !themeBean.isChoose() ? R.color.text_color_primary_alpha_50 : themeBean.getColor()));
            }
            binding.ivChooseTheme.setVisibility(!themeBean.isChoose() ? View.GONE : View.VISIBLE);
            binding.tvUserTheme.setText(!themeBean.isChoose() ? "使用" : "使用中");
            binding.tvThemeColor.setText(themeBean.getColorName());
            addChildClickViewIds(R.id.tvUserTheme);
        }
    }
}