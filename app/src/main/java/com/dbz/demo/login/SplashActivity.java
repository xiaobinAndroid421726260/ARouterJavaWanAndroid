package com.dbz.demo.login;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.dbz.demo.MainActivity;
import com.dbz.demo.R;


public class SplashActivity extends Activity {

    private ImageView ivLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo = findViewById(R.id.iv_logo);
        initView();
    }

    protected void initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3F, 1.0F);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ActivityUtils.startActivity(SplashActivity.this, MainActivity.class, android.R.anim.fade_in, android.R.anim.fade_out);
                ActivityUtils.finishActivity(SplashActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivLogo.setAnimation(alphaAnimation);
    }
}
