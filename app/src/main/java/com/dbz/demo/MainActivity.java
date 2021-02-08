package com.dbz.demo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.BaseActivity;
import com.dbz.base.ScrollToTop;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.demo.databinding.ActivityMainBinding;
import com.dbz.home.HomeFragment;
import com.dbz.network.retrofit.utils.LogUtils;
import com.dbz.project.ProjectFragment;
import com.dbz.square.SquareFragment;
import com.dbz.system.SystemFragment;
import com.dbz.wechat.WechatFragment;

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private HomeFragment mHomeFragment;
    private SquareFragment mSquareFragment;
    private WechatFragment mWechatFragment;
    private SystemFragment mSystemFragment;
    private ProjectFragment mProjectFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("WanAndroid");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.logo);
        binding.toolbar.setNavigationOnClickListener(v -> mViewModel.openDrawer.setValue(true));
        initFragment();
        initNavigation();
        mViewModel.openDrawer.observe(this, aBoolean -> {
        });
    }

    @Override
    protected void initData() {

    }

    private void initFragment() {
        setSelectedFragment(0);
    }

    @Override
    protected void initStatusColor() {
        super.initStatusColor();
        binding.actionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(mThemeColor)));
    }

    private void initNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_home) {
                setSelectedFragment(0);
            } else if (id == R.id.action_square) {
                setSelectedFragment(1);
            } else if (id == R.id.action_wechat) {
                setSelectedFragment(2);
            } else if (id == R.id.action_system) {
                setSelectedFragment(3);
            } else if (id == R.id.action_project) {
                setSelectedFragment(4);
            }
            return true;
        });
        binding.actionButton.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
            if (fragment instanceof ScrollToTop) {
                ((ScrollToTop) fragment).scrollToTop();
            }
        });
    }

    /**
     * 获取当前显示的Fragment实例
     */
//    private Fragment getCurrentFragment() {
//        //获取指定的fragment
//        Fragment navFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
//        Fragment fragment = navFragment.getChildFragmentManager().getPrimaryNavigationFragment();
//        if (fragment instanceof ScrollToTop) {
//            return fragment;
//        }
//        return null;
//    }
    private void setSelectedFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (position) {
            case 0:
                binding.toolbar.setTitle(getResources().getString(R.string.home));
                if (mHomeFragment == null) {
                    mHomeFragment = (HomeFragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).navigation();
                    transaction.add(R.id.fl_container, mHomeFragment, "home");
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case 1:
                binding.toolbar.setTitle(getResources().getString(R.string.square));
                if (mSquareFragment == null) {
                    mSquareFragment = (SquareFragment) ARouter.getInstance().build(RouterFragmentPath.Square.PAGER_SQUARE).navigation();
                    transaction.add(R.id.fl_container, mSquareFragment, "square");
                } else {
                    transaction.show(mSquareFragment);
                }
                break;
            case 2:
                binding.toolbar.setTitle(getResources().getString(R.string.wechat));
                if (mWechatFragment == null) {
                    mWechatFragment = (WechatFragment) ARouter.getInstance().build(RouterFragmentPath.Wechat.PAGER_WECHER).navigation();
                    transaction.add(R.id.fl_container, mWechatFragment, "wechat");
                } else {
                    transaction.show(mWechatFragment);
                }
                break;
            case 3:
                binding.toolbar.setTitle(getResources().getString(R.string.knowledge_system));
                if (mSystemFragment == null) {
                    mSystemFragment = (SystemFragment) ARouter.getInstance().build(RouterFragmentPath.System.PAGER_SYSTEM).navigation();
                    transaction.add(R.id.fl_container, mSystemFragment, "system");
                } else {
                    transaction.show(mSystemFragment);
                }
                break;
            case 4:
                binding.toolbar.setTitle(getResources().getString(R.string.project));
                if (mProjectFragment == null) {
                    mProjectFragment = (ProjectFragment) ARouter.getInstance().build(RouterFragmentPath.Project.PAGER_PROJECT).navigation();
                    transaction.add(R.id.fl_container, mProjectFragment, "project");
                } else {
                    transaction.show(mProjectFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的Fragment
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mSquareFragment != null) {
            transaction.hide(mSquareFragment);
        }
        if (mWechatFragment != null) {
            transaction.hide(mWechatFragment);
        }
        if (mSystemFragment != null) {
            transaction.hide(mSystemFragment);
        }
        if (mProjectFragment != null) {
            transaction.hide(mProjectFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search){
            ToastUtils.showShort("跳转搜索");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}