package com.dbz.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ViewAdapter {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"url", "placeholder"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(placeholderRes)
                    .into(imageView);
        }
    }

    @BindingAdapter("visible")
    public static void visible(View view, int visible){
        view.setVisibility(visible == 1 ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visible")
    public static void visible(View view, boolean visible){
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visible")
    public static void visible(View view, List<?> visible){
        view.setVisibility(visible.size() > 0 ? View.VISIBLE : View.GONE);
    }
}