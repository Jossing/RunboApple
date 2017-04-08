package com.jossing.runboapple.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.main.model.ApplePicture;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jossing , Create on 2017/4/1
 */

public class ApplePicturePagerAdapter extends PagerAdapter {

    private List<ImageView> viewList;
    private List<ApplePicture> applePictureList;
    private List<Boolean> applePictureLoaded; // 判断某一个图片是否已经加载过

    public ApplePicturePagerAdapter() {
        this.viewList = new ArrayList<>();
    }

    /**
     * 创建并初始化 ViewPager 中每一个 ImageView，同时初始化 applePictureLoaded 为全 false
     * @param count 图片个数
     */
    private void initViewList(Context context, int count) {
        viewList = new ArrayList<>();
        applePictureLoaded = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(lp);
            viewList.add(imageView);

            applePictureLoaded.add(false);
        }
        notifyDataSetChanged();
        // 手动加载 position 0 的图片
        if (count > 0) loadApplePicture(context, 0);
    }

    /**
     * 设置图片列表
     * @param list 图片列表
     */
    public void setApplePictureList(Context context, List<ApplePicture> list) {
        applePictureList = list;
        int count = applePictureList.size(); // 图片个数
        initViewList(context, count);
    }

    public ImageView getPager(int position) {
        return viewList.get(position);
    }

    public void loadApplePicture(Context context, int position) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) return;
        }
        /* 如果当前 page 的图片还未加载过，那么加载显示当前 page 的图片 */
        if (!applePictureLoaded.get(position)) {
            Log.e("loadPicture", "position: " + position);
            ImageView imageView = getPager(position);
            String url = applePictureList.get(position).getPicture().getFileUrl(context);
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
            applePictureLoaded.set(position, true); // 表明这一页已经加载过，无需再次加载
        }
        /* 如果不是第 0 页，并且未加载过，那么也加载它左边的一页 */
        int positionLeft = position - 1;
        if (position != 0 && !applePictureLoaded.get(positionLeft)) {
            Log.e("loadPicture", "position: " + positionLeft);
            ImageView imageViewLeft = getPager(positionLeft);
            String urlLeft = applePictureList.get(positionLeft).getPicture().getFileUrl(context);
            Glide.with(context)
                    .load(urlLeft)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageViewLeft);
            applePictureLoaded.set(positionLeft, true); // 表明这一页已经加载过，无需再次加载
        }
        /* 如果不是最后一页，并且未加载过，那么也加载它右边的一页 */
        int positionRight = position + 1;
        if (position != (getCount() - 1) && !applePictureLoaded.get(positionRight)) {
            Log.e("loadPicture", "position: " + positionRight);
            ImageView imageViewRight = getPager(positionRight);
            String urlRight = applePictureList.get(positionRight).getPicture().getFileUrl(context);
            Glide.with(context)
                    .load(urlRight)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageViewRight);
            applePictureLoaded.set(positionRight, true); // 表明这一页已经加载过，无需再次加载
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position + "(" + getCount() + ")";
    }

    @Override
    public int getItemPosition(Object object) {
        if (viewList.contains((ImageView) object)) {
            Log.e("view is contains", true + "");
            return viewList.indexOf((ImageView) object);
        } else {
            Log.e("view is contains", false + "");
            return POSITION_NONE;
        }
    }
}
