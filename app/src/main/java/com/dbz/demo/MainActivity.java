package com.dbz.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dbz.base.BaseActivity;
import com.dbz.base.ScrollToTop;
import com.dbz.base.ViewColorUtils;
import com.dbz.base.config.RouterActivityPath;
import com.dbz.base.config.RouterFragmentPath;
import com.dbz.base.event.EventTheme;
import com.dbz.demo.databinding.ActivityMainBinding;
import com.dbz.home.HomeFragment;
import com.dbz.project.ProjectFragment;
import com.dbz.square.SquareFragment;
import com.dbz.system.SystemFragment;
import com.dbz.wechat.WechatFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private static final String BOTTOM_INDEX = "bottom_index";

    private static final int FRAGMENT_HOME = 0x01;
    private static final int FRAGMENT_SQUARE = 0x02;
    private static final int FRAGMENT_WECHAT = 0x03;
    private static final int FRAGMENT_SYSTEM = 0x04;
    private static final int FRAGMENT_PROJECT = 0x05;

    private int index = FRAGMENT_HOME;

    private HomeFragment mHomeFragment;
    private SquareFragment mSquareFragment;
    private WechatFragment mWechatFragment;
    private SystemFragment mSystemFragment;
    private ProjectFragment mProjectFragment;

    private LinearLayout headerLayout;

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
    protected void setWindowConfigure() {
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("WanAndroid");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.open_drawer_white);
        binding.toolbar.setNavigationOnClickListener(v -> mViewModel.openDrawer.setValue(true));
        initFragment();
        initNavigation();
        mViewModel.openDrawer.observe(this, aBoolean -> {
            if (aBoolean) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            } else {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        mViewModel.theme.observe(this, aBoolean -> {
            if (aBoolean){
                mViewModel.theme.setValue(false);
                recreateActivity();
            }
        });
    }

    @Override
    protected void initData() {
        ViewColorUtils.setToolbarBackColor(this, binding.toolbar, binding.actionButton);
        headerLayout.setBackgroundColor(getResources().getColor(mThemeColor));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventTheme theme) {
        mViewModel.theme.setValue(true);
    }

    private void initFragment() {
        if (getSavedInstanceState() != null) {
            index = getSavedInstanceState().getInt(BOTTOM_INDEX);
        }
        setSelectedFragment(index);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != outState) {
            outState.putInt(BOTTOM_INDEX, index);
        }
    }

    private void initNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_home) {
                setSelectedFragment(FRAGMENT_HOME);
            } else if (id == R.id.action_square) {
                setSelectedFragment(FRAGMENT_SQUARE);
            } else if (id == R.id.action_wechat) {
                setSelectedFragment(FRAGMENT_WECHAT);
            } else if (id == R.id.action_system) {
                setSelectedFragment(FRAGMENT_SYSTEM);
            } else if (id == R.id.action_project) {
                setSelectedFragment(FRAGMENT_PROJECT);
            }
            return true;
        });
        binding.actionButton.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
            if (fragment instanceof ScrollToTop) {
                ((ScrollToTop) fragment).scrollToTop();
            }
        });
        View view = binding.navigation.getHeaderView(0);
        headerLayout = view.findViewById(R.id.ll_header_layout);
        binding.navigation.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_collect) {
                ToastUtils.showShort("我的收藏");
                mViewModel.openDrawer.setValue(false);
            } else if (item.getItemId() == R.id.nav_share) {
                ToastUtils.showShort("我的分享");
                mViewModel.openDrawer.setValue(false);
            } else if (item.getItemId() == R.id.nav_todo) {
                ToastUtils.showShort("TODO");
                mViewModel.openDrawer.setValue(false);
            } else if (item.getItemId() == R.id.nav_night_mode) {
                if (SPStaticUtils.getBoolean("switch_nightMode")) {
//                    textView.setText("夜间模式");
                    SPStaticUtils.put("switch_nightMode", false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
//                    textView.setText("日间模式");
                    SPStaticUtils.put("switch_nightMode", true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                ViewColorUtils.setToolbarBackColor(this, binding.toolbar, binding.actionButton);
                recreateActivity();
            } else if (item.getItemId() == R.id.nav_setting) {
                ARouter.getInstance().build(RouterActivityPath.Setting.PAGER_SETTING).navigation();
                mViewModel.openDrawer.setValue(false);
            } else if (item.getItemId() == R.id.nav_logout) {
                ToastUtils.showShort("退出登陆");
                mViewModel.openDrawer.setValue(false);
            }
            return true;
        });
    }

    /**
     * 重新创建Activity
     */
    private void recreateActivity(){
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }

    /**
     * 重新创建实例
     */
    @Override
    public void recreate() {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (mHomeFragment != null) {
                transaction.remove(mHomeFragment);
            }
            if (mSquareFragment != null) {
                transaction.remove(mSquareFragment);
            }
            if (mWechatFragment != null) {
                transaction.remove(mWechatFragment);
            }
            if (mSystemFragment != null) {
                transaction.remove(mSystemFragment);
            }
            if (mProjectFragment != null) {
                transaction.remove(mProjectFragment);
            }
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.recreate();
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
        index = position;
        switch (position) {
            case FRAGMENT_HOME:
                binding.toolbar.setTitle(getResources().getString(R.string.home));
                if (mHomeFragment == null) {
                    mHomeFragment = (HomeFragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).navigation();
                    transaction.add(R.id.fl_container, mHomeFragment, "home");
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case FRAGMENT_SQUARE:
                binding.toolbar.setTitle(getResources().getString(R.string.square));
                if (mSquareFragment == null) {
                    mSquareFragment = (SquareFragment) ARouter.getInstance().build(RouterFragmentPath.Square.PAGER_SQUARE).navigation();
                    transaction.add(R.id.fl_container, mSquareFragment, "square");
                } else {
                    transaction.show(mSquareFragment);
                }
                break;
            case FRAGMENT_WECHAT:
                binding.toolbar.setTitle(getResources().getString(R.string.wechat));
                if (mWechatFragment == null) {
                    mWechatFragment = (WechatFragment) ARouter.getInstance().build(RouterFragmentPath.Wechat.PAGER_WECHER).navigation();
                    transaction.add(R.id.fl_container, mWechatFragment, "wechat");
                } else {
                    transaction.show(mWechatFragment);
                }
                break;
            case FRAGMENT_SYSTEM:
                binding.toolbar.setTitle(getResources().getString(R.string.knowledge_system));
                if (mSystemFragment == null) {
                    mSystemFragment = (SystemFragment) ARouter.getInstance().build(RouterFragmentPath.System.PAGER_SYSTEM).navigation();
                    transaction.add(R.id.fl_container, mSystemFragment, "system");
                } else {
                    transaction.show(mSystemFragment);
                }
                break;
            case FRAGMENT_PROJECT:
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
        if (item.getItemId() == R.id.action_search) {
            ToastUtils.showShort("跳转搜索");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}