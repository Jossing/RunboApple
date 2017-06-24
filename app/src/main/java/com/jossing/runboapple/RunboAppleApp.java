package com.jossing.runboapple;

import android.animation.ValueAnimator;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @author Jossing , Create on 2017/3/16
 */

public class RunboAppleApp extends Application {
    // 设备屏幕缩放级别
    public static float density;

    private static Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();
        density = getResources().getDisplayMetrics().density;

        bmobInitialize();
    }

    /**
     * 初始化 Bmob SDK
     */
    private void bmobInitialize() {
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("e12d3488c374647cc0690b120d845a44")// 设置 appKey
                .setConnectTimeout(15)// 请求超时时间（单位为秒）：默认 15s
                .setUploadBlockSize(1024*1024)// 文件分片上传时每片的大小（单位字节），默认512*1024
                .setFileExpiration(2500)// 文件的过期时间(单位为秒)：默认 1800s
                .build();
        Bmob.initialize(config);
    }

    /**
     * 弹出一条 全局 Toast
     * @param msg Toast 内容
     * @param duration Toast 持续时间
     */
    public static void toastShow(Context context, String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        } else {
            toast.setText(msg);
            toast.setDuration(duration);
        }
        toast.show();
    }

    /**
     * 动画显示 view
     * @param view 视图
     */
    public static void animationShowView(final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setAlpha(value);
                if (value == 0f){
                    // start
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
        animator.start();
    }

    /**
     * 动画隐藏 view
     * @param view 视图
     */
    public static void animationHideView(final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f).setDuration(200);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setAlpha(value);
                if (value == 0f){
                    // end
                    view.setVisibility(View.GONE);
                }
            }
        });
        animator.start();
    }

    /**
     * 把文件路径转换为 content uri 以适配 Android N
     * @param file 文件对象
     */
    public static Uri getContentUri(Context context, File file) {
        return FileProvider.getUriForFile(context, "com.jossing.runboapple.fileprovider", file);
    }

}
