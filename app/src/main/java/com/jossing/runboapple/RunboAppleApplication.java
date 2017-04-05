package com.jossing.runboapple;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @author Jossing , Create on 2017/3/16
 */

public class RunboAppleApplication extends Application {
    // 设备屏幕缩放级别
    public static float density;

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

}
