package com.dbz.home;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * description:
 *
 * @author Db_z
 * date 2020/6/5 15:45
 * @version V1.0
 */
public class BannerImageAdapter extends BannerAdapter<String, BannerImageAdapter.BannerViewHolder> {

    public BannerImageAdapter(List<String> data) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(data);
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, String data, int position, int size) {
        Glide.with(holder.imageView.getContext()).load(data).into(holder.imageView);
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public BannerViewHolder(ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}